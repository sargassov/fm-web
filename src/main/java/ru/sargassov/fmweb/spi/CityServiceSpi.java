package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.intermediate_entites.City;
import ru.sargassov.fmweb.repositories.CityRepository;

import java.util.List;

public interface CityServiceSpi {

    List<City> getAllCities();
}
