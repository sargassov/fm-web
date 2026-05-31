package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.intermediate_entities.Cup;
import ru.sargassov.fmweb.intermediate_entities.Draw;
import ru.sargassov.fmweb.intermediate_entities.League;
import ru.sargassov.fmweb.intermediate_entities.User;

@Component
@AllArgsConstructor
@Slf4j
public class DrawConverter {
    public Draw getIntermediateEntityFromString(String match, User user, int tourCount, League league, Cup cup) {
        var draw = new Draw();
        draw.setUser(user);
        draw.setLeague(league);
        draw.setCup(cup);
        draw.setMatch(match);
        draw.setTourNumber(tourCount);
        return draw;
    }
}
