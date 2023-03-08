package ru.sargassov.fmweb.services.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_entities.City;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.repositories.intermediate_repositories.CityIntermediateRepository;
import ru.sargassov.fmweb.spi.intermediate_spi.CityIntermediateServiceSpi;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class CityIntermediateService implements CityIntermediateServiceSpi {
    private CityIntermediateRepository repository;

    @Override
    public List<City> save(List<City> cities) {
        return repository.saveAll(cities);
    }

    @Override
    public City findByCityEntityIdAndUser(Long cityEntityId, User user) {
        return repository.findByCityEntityIdAndUser(cityEntityId, user);
    }

    @Override
    public void assignTeamsToCities(List<Team> teams) {
        var cities = teams.stream()
                .map(team -> assignCurrentTeamToCity(team))
                .collect(Collectors.toList());
        save(cities);
    }

    private City assignCurrentTeamToCity(Team team) {
        var city = team.getCity();
        city.getTeams().add(team);
        return city;
    }
}
