package ru.sargassov.fmweb.services.entity;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.intermediate_entities.Cortage;
import ru.sargassov.fmweb.intermediate_entities.Match;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.spi.intermediate_spi.CortageIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.MatchIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.TeamIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.entity.MatrixCreateServiceSpi;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MatrixService implements MatrixCreateServiceSpi {
    private CortageIntermediateServiceSpi cortageIntermediateService;
    private TeamIntermediateServiceSpi teamIntermediateService;
    private MatchIntermediateServiceSpi matchIntermediateService;

    @Override
    public void createMatrix(User user) {
        var teamsSortedByName = teamIntermediateService.findAllByUserSortedByName(user);
        var cortages = new ArrayList<Cortage>();

        for (int x = 0; x < teamsSortedByName.size(); x++) {
            var cortage = new Cortage();

            var matches = cortage.getMatchList();
            cortage.setTeam(teamsSortedByName.get(x));
            for (int y = 0; y < teamsSortedByName.size(); y++) {
                if (x == y) {
                    var impossibleMatch = new Match();
                    impossibleMatch.setImpossibleMatch(true);
                    impossibleMatch.setHome(teamsSortedByName.get(x));
                    impossibleMatch.setAway(teamsSortedByName.get(y));
                    impossibleMatch.setUser(user);
                    matchIntermediateService.save(impossibleMatch);
                    matches.add(impossibleMatch);
                    continue;
                }
                var match = matchIntermediateService.findCurrentMatch(
                        teamsSortedByName.get(x), teamsSortedByName.get(y), user);
                matches.add(match);
            }
            cortage.setUser(user);
            cortages.add(cortage);
        }
        cortageIntermediateService.save(cortages);
        assignCortagesToMatches(cortages);
    }

    private void assignCortagesToMatches(List<Cortage> cortages) {
        for (var c : cortages) {
            for (var m : c.getMatchList()) {
                m.setCortage(c);
            }
            matchIntermediateService.save(c.getMatchList());
        }
    }

    @Override
    public List<Cortage> getActualMatrix() {
        return cortageIntermediateService.getByUser(UserHolder.user);
    }
}
