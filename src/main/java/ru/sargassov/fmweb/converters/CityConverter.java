package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.City;
import ru.sargassov.fmweb.dto.League;
import ru.sargassov.fmweb.entities.CityEntity;

@Component
@AllArgsConstructor
public class CityConverter {
    private final League league;

    public City entityToDto(CityEntity cityEntity){
        City cDto = new City();
        cDto.setId(cityEntity.getId());
        cDto.setName(cityEntity.getName());
        cDto.setLeague(league);
        return cDto;
    }
}
