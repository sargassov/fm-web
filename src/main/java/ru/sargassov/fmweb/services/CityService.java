package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.CityConverter;
import ru.sargassov.fmweb.intermediate_entities.City;
import ru.sargassov.fmweb.entity_repositories.CityRepository;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_spi.CityIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.LeagueIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.CityServiceSpi;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CityService implements CityServiceSpi {
    private final CityRepository cityRepository;
    private final CityConverter cityConverter;
    private final CityIntermediateServiceSpi cityIntermediateService;
    private final LeagueIntermediateServiceSpi leagueIntermediateService;

    @Override
    @Transactional
    public List<City> getAllCities(){
        cityRepository.findAll();
        return null;
    }

    @Override
    public void loadCities(User user) {
        var cityEntityes = cityRepository.findAll();
        var league = leagueIntermediateService.getLeague(user);
        var notSavedCities = cityEntityes
                .stream()
                .map(cityEntity -> cityConverter.getIntermediateEntityFromEntity(cityEntity, league, user))
                .collect(Collectors.toList());
        cityIntermediateService.save(notSavedCities);
    }
}
