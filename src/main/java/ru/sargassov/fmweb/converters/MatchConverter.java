package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.intermediate_entities.*;
import ru.sargassov.fmweb.intermediate_spi.TeamIntermediateServiceSpi;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static ru.sargassov.fmweb.constants.TextConstant.DASH_DELIVER;

@Component
@AllArgsConstructor
public class MatchConverter {
    private final TeamIntermediateServiceSpi teamIntermediateService;

    public Match getIntermediateEntityFromDraw(Draw draw, Day day, User user) {
        var match = new Match();
        var matchDescription = draw.getMatch().split(DASH_DELIVER);
        var homeTeam = teamIntermediateService.findByName(matchDescription[0]);
        var awayTeam = teamIntermediateService.findByName(matchDescription[1]);
        match.setUser(user);
        match.setHome(homeTeam);
        match.setAway(awayTeam);
        match.setStadium(homeTeam.getStadium());
        match.setTourDay(day);
        match.setCortage(null);
        match.setImpossibleMatch(false);
        match.setGoals(new ArrayList<>());
        match.setMatchPassed(false);
        return match;
    }
}
