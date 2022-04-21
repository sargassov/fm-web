package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.*;
import ru.sargassov.fmweb.entities.TeamEntity;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class TeamConverter {
    private final League league;
    private final StadiumConverter stadiumConverter;
    private final CityConverter cityConverter;

    public Team entityToDto(TeamEntity teamEntity){
        Team tDto = new Team();
        tDto.setId(teamEntity.getId());
        tDto.setName(teamEntity.getName());
        tDto.setHeadCoach(new HeadCoach(teamEntity.getManager()));
        tDto.setWealth(teamEntity.getWealth());
        tDto.setLeague(league);
        tDto.setStadium(stadiumConverter.entityToDto(teamEntity.getStadiumEntity(), tDto));
        tDto.setCity(cityConverter.entityToDto(teamEntity.getCityEntity()));
        //------------------------
        tDto.setStartWealth(tDto.getWealth());
        tDto.setCoaches(new ArrayList<>());
//        tDto.setPlayerList(team.getPlayerList().stream()
//                .map(playerConverter::entityToDto)
//                .collect(Collectors.toList()));
        tDto.setLoans(new ArrayList<>());
//    private List<Market> markets;
//    private Placement placement;
        return tDto;
    }
}
