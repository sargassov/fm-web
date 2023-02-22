package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_entities.Match;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_repositories.MatchIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.DayIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.MatchIntermediateServiceSpi;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
    public Match findCurrentMatch(Team homeTeam, Team awayTeam, User user) {
        return repository.findByHomeAndAwayAndUser(homeTeam, awayTeam, user);
    }

    @Override
    public List<Match> findAllByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    public List<Match> findByUserAndCountOfTour(User user, int countOfTour) {
        return repository.findByUserAndCountOfTour(user, countOfTour);
    }

    @Override
    @Transactional
    public void assignTourDayToMatches(User user) {
        var tourCounter = 1;
        var calendar = dayIntermediateService.findByUser(user);
        var matchList = new ArrayList<Match>();
        for (var day : calendar) {
            if (day.isMatch()) {
                var allMatchesFromCurrentTour = repository.findByUserAndCountOfTour(user, tourCounter);
                for (var match : allMatchesFromCurrentTour) {
                    match.setTourDay(day);
                }
                matchList.addAll(allMatchesFromCurrentTour);
            }
        }
        save(matchList);
    }
}
