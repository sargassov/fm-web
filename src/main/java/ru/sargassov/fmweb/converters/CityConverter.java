package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.intermediate_entities.City;
import ru.sargassov.fmweb.intermediate_entities.League;
import ru.sargassov.fmweb.entities.CityEntity;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_spi.CityIntermediateServiceSpi;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class CityConverter {
    private final CityIntermediateServiceSpi cityIntermediateService;

    public City getIntermediateEntityFromEntity(CityEntity cityEntity, Team team, User user, League league){
        City city = new City();
        city.setLeague(league);
        city.setName(cityEntity.getName());
        city.setUser(user);

        return cityIntermediateService.findExistsOrSave(city, team);
    }
}
