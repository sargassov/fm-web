package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.CityDto;
import ru.sargassov.fmweb.dto.LeagueDto;
import ru.sargassov.fmweb.entities.City;

@Component
@AllArgsConstructor
public class CityConverter {
    private final LeagueDto leagueDto;

    public CityDto entityToDto(City city){
        CityDto cDto = new CityDto();
        cDto.setId(city.getId());
        cDto.setName(city.getName());
        cDto.setLeague(leagueDto);
        return cDto;
    }
}
