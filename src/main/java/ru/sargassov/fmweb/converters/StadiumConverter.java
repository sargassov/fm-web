package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.CityDto;
import ru.sargassov.fmweb.dto.LeagueDto;
import ru.sargassov.fmweb.dto.StadiumDto;
import ru.sargassov.fmweb.dto.TeamDto;
import ru.sargassov.fmweb.entities.Stadium;

@Component
@AllArgsConstructor
public class StadiumConverter {
    private final LeagueDto leagueDto;
    private final CityConverter cityConverter;

    public StadiumDto entityToDto(Stadium stadium, TeamDto tDto){
        StadiumDto sDto = new StadiumDto();
        sDto.setId(stadium.getId());
        sDto.setTitle(stadium.getTitle());
        sDto.setLeague(leagueDto);
        sDto.setFullCapacity(stadium.getFullCapacity());
        sDto.setCity(cityConverter.entityToDto(stadium.getCity()));
        sDto.setTeam(tDto);
        return sDto;
    }
}
