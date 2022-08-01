package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_entites.City;
import ru.sargassov.fmweb.repositories.CityRepository;
import ru.sargassov.fmweb.spi.CityServiceSpi;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class CityService implements CityServiceSpi {
    private final CityRepository cityRepository;

    @Override
    @Transactional
    public List<City> getAllCities(){
        cityRepository.findAll();
        return null;
    }
}
