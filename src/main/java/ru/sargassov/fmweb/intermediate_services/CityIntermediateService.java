package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_entities.City;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_repositories.CityIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.CityIntermediateServiceSpi;

import java.util.ArrayList;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class CityIntermediateService implements CityIntermediateServiceSpi {
    private CityIntermediateRepository repository;
    @Override
    public City findExistsOrSave(City city, Team team) {
        var cityName = city.getName();
        var existsCity = repository.findByName(cityName);
        if (existsCity.isPresent()) {
            var presentCity = existsCity.get();
            if (presentCity.getTeams() != null) {
                var teamList = presentCity.getTeams();
                teamList.add(team);
            } else {
                var teamList = new ArrayList<Team>();
                teamList.add(team);
                presentCity.setTeams(teamList);
            }
            return repository.save(presentCity);
        } else {
            var teamList = new ArrayList<Team>();
            teamList.add(team);
            city.setTeams(teamList);
        }
        return repository.save(city);
    }
}
