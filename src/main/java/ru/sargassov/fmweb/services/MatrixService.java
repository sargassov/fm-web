package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api_temporary_classes_group.MatrixApi;
import ru.sargassov.fmweb.intermediate_entites.Cortage;
import ru.sargassov.fmweb.intermediate_entites.Team;
import ru.sargassov.fmweb.intermediate_entites.days.Match;
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
    private MatrixApi matrixApi;

    @Override
    public void createMatrix() {
        List<Cortage> cortages = matrixApi.constructMatrix();
        List<Team> teamList = teamService.findAll().stream()
                .sorted(Comparator.comparing(Team::getName))
                .collect(Collectors.toList());

        for (int x = 0; x < teamList.size(); x++) {
            Cortage cortage = new Cortage();
            List<Match> matches = cortage.getMatchList();
            cortage.setTeam(teamList.get(x));
            for (int y = 0; y < teamList.size(); y++) {
                if (x == y) {
                    matches.add(new Match(true));
                    continue;
                }
                Match match = calendarService.findCurrentMatch(teamList.get(x), teamList.get(y));
                matches.add(match);
            }
            cortages.add(cortage);
        }
    }

    @Override
    public List<Cortage> getActualMatrix() {
        return matrixApi.getMatrix();
    }
}
