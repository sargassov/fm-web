package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.constants.TextConstant;
import ru.sargassov.fmweb.dto.text_responses.TextResponse;
import ru.sargassov.fmweb.intermediate_entities.Day;
import ru.sargassov.fmweb.spi.DayServiceSpi;
import ru.sargassov.fmweb.spi.NewDayServiceSpi;
import ru.sargassov.fmweb.spi.TeamServiceSpi;
import ru.sargassov.fmweb.spi.UserServiceSpi;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class NewDayService implements NewDayServiceSpi {

    private static final String MATCH_DONT_PLAYED = TextConstant.MATCH_DONT_PLAYED;

    private final DayServiceSpi dayService;
    private final UserServiceSpi userService;
    private final TeamServiceSpi teamService;

    @Override
    @Transactional
    public void createNewDay() {
//        if (dayService.isMatchday()) { TODO Добавить эту функцию
//            log.error(MATCH_DONT_PLAYED);
//            throw new NewDayException(MATCH_DONT_PLAYED);
//        }

        List<String> notesOfChanges = new ArrayList<>();
        notesOfChanges.add(addDate());
        notesOfChanges.add(openYouthAcademy());
        notesOfChanges.addAll(setTrainingEffects());
        notesOfChanges.addAll(setFinanceUpdates());
        notesOfChanges.addAll(setMarketingChanges());
        setPlayerRecovery();

        Day currentDay = dayService.getActualDay();
        currentDay.setNoteOfChanges(notesOfChanges);
    }

    private void setPlayerRecovery() {
        teamService.setPlayerRecovery();
    }

    @Override
    public List<TextResponse> loadChanges() {
        Day actualDay = dayService.getActualDay();
        List<String> notesOfChanges = actualDay.getNoteOfChanges();
        return new ArrayList<>(TextConstant.dropStringsIntoTextResponses(notesOfChanges));
    }

    private List<String> setMarketingChanges() {
        return teamService.setMarketingChanges();
    }

    private List<String> setFinanceUpdates() {
        return teamService.setFinanceUpdates();
    }

    private List<String> setTrainingEffects() {
        List<String> noteOfChanges = teamService.setTrainingEffects();
        return noteOfChanges;
    }

    private String openYouthAcademy() {
        userService.visit(false);
        return "Now you can invoke players from youth academy";
    }

    private String addDate() {
        return dayService.addDate();
    }
}
