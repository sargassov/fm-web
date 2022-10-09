package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.intermediate_entities.Draw;
import ru.sargassov.fmweb.intermediate_entities.User;

@Component
@AllArgsConstructor
@Slf4j
public class DrawConverter {
    public Draw getIntermediateEntityFromString(String match, User user, int tourCount) {
        var draw = new Draw();
        draw.setUser(user);
        draw.setMatch(match);
        draw.setTourNumber(tourCount);
        return draw;
    }
}
