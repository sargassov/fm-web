package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.League;
import ru.sargassov.fmweb.dto.Stadium;
import ru.sargassov.fmweb.dto.Team;
import ru.sargassov.fmweb.entities.StadiumEntity;

@Component
@AllArgsConstructor
public class StadiumConverter {
    private final League league;
    private final CityConverter cityConverter;

    public Stadium entityToDto(StadiumEntity stadiumEntity, Team tDto){
        Stadium sDto = new Stadium();
        sDto.setId(stadiumEntity.getId());
        sDto.setTitle(stadiumEntity.getTitle());
        sDto.setLeague(league);
        sDto.setFullCapacity(stadiumEntity.getFullCapacity());
        sDto.setCity(cityConverter.entityToDto(stadiumEntity.getCityEntity()));
        sDto.setTeam(tDto);
        return sDto;
    }
}
