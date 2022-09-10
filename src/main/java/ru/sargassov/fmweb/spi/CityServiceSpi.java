package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.intermediate_entities.City;

import java.util.List;

public interface CityServiceSpi {

    List<City> getAllCities();
}
