package ru.sargassov.fmweb.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.dto.CityDto;
import ru.sargassov.fmweb.repositories.CityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public List<CityDto> getAllCities(){
        cityRepository.findAll();
        return null;
    }
}
