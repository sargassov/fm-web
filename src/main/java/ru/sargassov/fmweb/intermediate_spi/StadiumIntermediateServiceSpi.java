package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.Stadium;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

public interface StadiumIntermediateServiceSpi {
    List<Stadium> save(List<Stadium> notSavedStadiums);

    Stadium findByStadiumEntityIdAndUser(Long stadiumEntityId, User user);

    Stadium save(Stadium stadium);

    void assignTeamsToStadiums(List<Team> teams);
}
