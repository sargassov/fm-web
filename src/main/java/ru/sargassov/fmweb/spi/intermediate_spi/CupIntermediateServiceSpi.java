package ru.sargassov.fmweb.spi.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.Cup;
import ru.sargassov.fmweb.intermediate_entities.User;

public interface CupIntermediateServiceSpi {

    Cup save(Cup cup);

    Cup getCup(User user);
}
