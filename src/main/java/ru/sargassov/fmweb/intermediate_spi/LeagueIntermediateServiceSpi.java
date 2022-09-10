package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.League;
import ru.sargassov.fmweb.intermediate_entities.User;

public interface LeagueIntermediateServiceSpi {
    void save(League league);

    League getLeague(User user);
}
