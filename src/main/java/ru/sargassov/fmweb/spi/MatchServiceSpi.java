package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.intermediate_entities.User;

public interface MatchServiceSpi {
    void loadmatches(User user);
}
