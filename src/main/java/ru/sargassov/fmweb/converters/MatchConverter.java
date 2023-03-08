package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.intermediate_entities.*;
import ru.sargassov.fmweb.spi.intermediate_spi.TeamIntermediateServiceSpi;

import java.util.ArrayList;

import static ru.sargassov.fmweb.constants.TextConstant.DASH_DELIVER;

@Component
@AllArgsConstructor
public class MatchConverter {
    private final TeamIntermediateServiceSpi teamIntermediateService;

    public Match getIntermediateEntityFromDraw(Draw draw, User user) {
        var match = new Match();
        var matchDescription = draw.getMatch().split(DASH_DELIVER);
        var homeTeam = teamIntermediateService.findByNameAndUser(matchDescription[0], user);
        var awayTeam = teamIntermediateService.findByNameAndUser(matchDescription[1], user);
        match.setUser(user);
        match.setHome(homeTeam);
        match.setAway(awayTeam);
        match.setCountOfTour(draw.getTourNumber());
        match.setStadium(homeTeam.getStadium());
        match.setImpossibleMatch(false);
        match.setGoals(new ArrayList<>());
        match.setMatchPassed(false);
        return match;
    }
}
