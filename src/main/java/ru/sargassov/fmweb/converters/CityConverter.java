package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.intermediate_entities.City;
import ru.sargassov.fmweb.intermediate_entities.League;
import ru.sargassov.fmweb.entities.CityEntity;
import ru.sargassov.fmweb.intermediate_entities.User;

@Component
@AllArgsConstructor
public class CityConverter {

    public City getIntermediateEntityFromEntity(CityEntity cityEntity, League league, User user){
        City city = new City();
        city.setLeague(league);
        city.setName(cityEntity.getName());
        city.setUser(user);
        city.setCityEntityId(cityEntity.getId());
        return city;
    }
}
