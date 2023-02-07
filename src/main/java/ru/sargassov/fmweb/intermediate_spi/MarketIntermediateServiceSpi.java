package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.Market;
import ru.sargassov.fmweb.intermediate_entities.Team;

import java.util.List;

public interface MarketIntermediateServiceSpi {
    Market save(Market market);
    List<Market> findByTeam(Team userTeam);
}
