package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.Match;
import ru.sargassov.fmweb.intermediate_entities.Team;

import java.util.List;

public interface MatchIntermediateServiceSpi {
    List<Match> save(List<Match> matches);

    Match save(Match impossibleMatch);

    Match findCurrentMatch(Team team, Team team1);
}
