package ru.sargassov.fmweb.services.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.GoalConverter;
import ru.sargassov.fmweb.intermediate_entities.Goal;
import ru.sargassov.fmweb.intermediate_entities.Match;
import ru.sargassov.fmweb.repositories.intermediate_repositories.GoalIntermediateRepository;
import ru.sargassov.fmweb.spi.intermediate_spi.GoalIntermediateServiceSpi;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class GoalIntermediateService implements GoalIntermediateServiceSpi {

    private GoalIntermediateRepository repository;

    private GoalConverter goalConverter;
    @Override
    public List<Goal> saveAllByMatches(List<Match> matches) {
        var goals = new ArrayList<Goal>();
        matches.forEach(match -> goals.addAll(goalConverter.getGoalsByMatch(match)));
        return repository.saveAll(goals);
    }


}
