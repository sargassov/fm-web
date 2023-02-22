package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.entities.CityEntity;
import ru.sargassov.fmweb.intermediate_entities.City;
import ru.sargassov.fmweb.intermediate_entities.Stadium;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

public interface CityIntermediateServiceSpi {

    List<City> save(List<City> notSavedCities);

    City findByCityEntityIdAndUser(Long cityEntityId, User user);

    void assignTeamsToCities(List<Team> teams);
}
