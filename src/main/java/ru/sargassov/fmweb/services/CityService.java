package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.dto.City;
import ru.sargassov.fmweb.repositories.CityRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public List<City> getAllCities(){
        cityRepository.findAll();
        return null;
    }
}
