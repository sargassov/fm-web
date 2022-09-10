package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_entities.Match;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_repositories.MatchIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.DayIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.MatchIntermediateServiceSpi;

import java.util.List;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class MatchIntermediateService implements MatchIntermediateServiceSpi {
    private final MatchIntermediateRepository repository;
    private final DayIntermediateServiceSpi dayIntermediateService;

    @Override
    public List<Match> save(List<Match> matches) {
        return repository.saveAll(matches);
    }

    @Override
    public Match save(Match match) {
        return repository.save(match);
    }

    @Override
    public Match findCurrentMatch(Team homeTeam, Team awayTeam) {
        var allDaysWithMatches = dayIntermediateService.findAllWhereIsMatchTrue();

        for(var tour : allDaysWithMatches) {
            for (var match : tour.getMatches()) {
                var home = match.getHome();
                var away = match.getAway();
                if (home.equals(homeTeam) && away.equals(awayTeam)) {
                    return match;
                }
            }
        }
    }
}
