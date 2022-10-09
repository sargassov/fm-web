package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.Match;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

public interface MatchIntermediateServiceSpi {
    List<Match> save(List<Match> matches);

    Match save(Match impossibleMatch);

    Match findCurrentMatch(Team homeTeam, Team awayTeam, User user);

    List<Match> findAllByUser(User user);

    List<Match> findByUserAndCountOfTour(User user, int countOfTour);

    void assignTourDayToMatches(User user);
}
