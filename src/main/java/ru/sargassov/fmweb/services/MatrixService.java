package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api_temporary_classes_group.MatrixApi;
import ru.sargassov.fmweb.intermediate_entities.Cortage;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.Match;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_spi.CortageIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.DayIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.MatchIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.TeamIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.CalendarServiceSpi;
import ru.sargassov.fmweb.spi.MatrixCreateServiceSpi;
import ru.sargassov.fmweb.spi.TeamServiceSpi;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MatrixService implements MatrixCreateServiceSpi {

    private TeamServiceSpi teamService;
    private CalendarServiceSpi calendarService;
    private CortageIntermediateServiceSpi cortageIntermediateService;
    private TeamIntermediateServiceSpi teamIntermediateService;
    private MatchIntermediateServiceSpi matchIntermediateService;

    @Override
    public void createMatrix(User user) {

        var cortages = cortageIntermediateService.constructMatrix();
        var teamsSortedByName = teamIntermediateService.findAllSortedByName();

        for (int x = 0; x < teamsSortedByName.size(); x++) {
            var cortage = new Cortage();
            var matches = cortage.getMatchList();
            cortage.setTeam(teamsSortedByName.get(x));
            for (int y = 0; y < teamsSortedByName.size(); y++) {
                if (x == y) {
                    var impossibleMatch = new Match();
                    matchIntermediateService.save(impossibleMatch);
                    matches.add(impossibleMatch);
                    continue;
                }
                var match = matchIntermediateService.findCurrentMatch(
                        teamsSortedByName.get(x), teamsSortedByName.get(y));
                matches.add(match);
            }
            cortage.setUser(user);
            cortageIntermediateService.save(cortage);
        }
    }

    @Override
    public List<Cortage> getActualMatrix() {
        return null;
    }
}
