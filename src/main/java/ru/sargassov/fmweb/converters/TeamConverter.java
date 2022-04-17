package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.apache.catalina.Manager;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.*;
import ru.sargassov.fmweb.entities.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TeamConverter {
    private final LeagueDto leagueDto;
    private final StadiumConverter stadiumConverter;
    private final CityConverter cityConverter;

    public TeamDto entityToDto(Team team){
        TeamDto tDto = new TeamDto();
        tDto.setId(team.getId());
        tDto.setName(team.getName());
        tDto.setHeadCoach(new HeadCoach(team.getManager()));
        tDto.setWealth(team.getWealth());
        tDto.setLeague(leagueDto);
        tDto.setStadium(stadiumConverter.entityToDto(team.getStadium(), tDto));
        tDto.setCity(cityConverter.entityToDto(team.getCity()));
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
