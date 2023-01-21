package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.intermediate_entities.Goal;
import ru.sargassov.fmweb.intermediate_entities.Match;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class GoalConverter {
    public List<Goal> getGoalsByMatch(Match match) {
        return match.getGoals();
    }
}
