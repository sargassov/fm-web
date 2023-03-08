package ru.sargassov.fmweb.services.entity;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.constants.TextConstant;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.dto.text_responses.TextResponse;
import ru.sargassov.fmweb.spi.intermediate_spi.DayIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.TeamIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.entity.NewDayServiceSpi;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class NewDayService implements NewDayServiceSpi {

    private static final String MATCH_DONT_PLAYED = TextConstant.MATCH_DONT_PLAYED;
    private final DayIntermediateServiceSpi dayIntermediateService;
    private final TeamIntermediateServiceSpi teamIntermediateService;

    @Override
    @Transactional
    public Boolean createNewDay() {
        var currentDay = dayIntermediateService.findByPresent();
        if (currentDay.isMatch()) {
            var userTeamMatch = currentDay.getUserTeamMatch(UserHolder.user.getUserTeam());
            if (!userTeamMatch.isMatchPassed()) {
                return false;
            }
        }

        var notesOfChanges = new ArrayList<String>();
        notesOfChanges.add(addDate());
        notesOfChanges.add(openYouthAcademy());
        notesOfChanges.addAll(setTrainingEffects());
        notesOfChanges.addAll(setFinanceUpdates());
        notesOfChanges.addAll(setMarketingChanges());
        setPlayerRecovery();

        var notesOfChangesArray = new String[notesOfChanges.size()];
        var i = 0;
        for (var s : notesOfChanges) {
            notesOfChangesArray[i] = s;
            i++;
        }
        var newCurrentDay = dayIntermediateService.findByPresent();
        newCurrentDay.setNoteOfChanges(notesOfChangesArray);
        return true;
    }

    private void setPlayerRecovery() {
        teamIntermediateService.setPlayerRecovery();
    }

    @Override
    public List<TextResponse> loadChanges() {
        var actualDay = dayIntermediateService.findByPresent();
        var notesOfChanges = actualDay.getNoteOfChanges();
        return new ArrayList<>(TextConstant.dropStringsIntoTextResponses(Arrays.stream(notesOfChanges).collect(Collectors.toList())));
    }

    private List<String> setMarketingChanges() {
        return teamIntermediateService.setMarketingChanges();
    }

    private List<String> setFinanceUpdates() {
        return teamIntermediateService.setFinanceUpdates();
    }

    private List<String> setTrainingEffects() {
        return teamIntermediateService.setTrainingEffects();
    }

    private String openYouthAcademy() {
        var user = UserHolder.user;
        user.setYouthAcademyVisited(false);
        return "Now you can invoke players from youth academy";
    }

    private String addDate() {
        return dayIntermediateService.addDate();
    }
}
