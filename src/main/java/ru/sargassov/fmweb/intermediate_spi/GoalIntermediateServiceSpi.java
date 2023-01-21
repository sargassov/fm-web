package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.Goal;
import ru.sargassov.fmweb.intermediate_entities.Match;

import java.util.List;

public interface GoalIntermediateServiceSpi {
    List<Goal> saveAllByMatches(List<Match> matches);
}
