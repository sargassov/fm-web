package ru.sargassov.fmweb.spi.entity;

import ru.sargassov.fmweb.intermediate_entities.City;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

public interface CityServiceSpi {

    List<City> getAllCities();

    void loadCities(User user);
}
