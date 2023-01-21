package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.Market;

public interface MarketIntermediateServiceSpi {
    Market save(Market market);
}
