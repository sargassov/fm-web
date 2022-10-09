package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.MatchConverter;
import ru.sargassov.fmweb.intermediate_entities.Match;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_spi.DrawIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.MatchIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.MatchServiceSpi;

import java.util.ArrayList;

@Service
@AllArgsConstructor
@Slf4j
public class MatchService implements MatchServiceSpi {
    private DrawIntermediateServiceSpi drawIntermediateService;
    private MatchIntermediateServiceSpi matchIntermediateService;
    private MatchConverter matchConverter;
    @Override
    public void loadmatches(User user) {
        var drawList = drawIntermediateService.findAllByUser(user);
        var notSavedMatches = new ArrayList<Match>();
        for (var draw : drawList) {
            var match = matchConverter.getIntermediateEntityFromDraw(draw, user);
            notSavedMatches.add(match);
        }
        matchIntermediateService.save(notSavedMatches);
    }
}
