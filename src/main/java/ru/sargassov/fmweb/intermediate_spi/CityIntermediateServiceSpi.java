package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.City;
import ru.sargassov.fmweb.intermediate_entities.Team;

public interface CityIntermediateServiceSpi {
    City findExistsOrSave(City city, Team team);
}
