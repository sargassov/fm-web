package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.*;
import ru.sargassov.fmweb.entities.TeamEntity;
import ru.sargassov.fmweb.intermediate_entites.HeadCoach;
import ru.sargassov.fmweb.intermediate_entites.League;
import ru.sargassov.fmweb.intermediate_entites.Team;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

@Component
@AllArgsConstructor
public class TeamConverter {
    private final League league;
    private final StadiumConverter stadiumConverter;
    private final CityConverter cityConverter;

    public Team entityToIntermediateEntity(TeamEntity teamEntity){
        Team tDto = new Team();
        tDto.setId(teamEntity.getId());
        tDto.setName(teamEntity.getName());
        tDto.setHeadCoach(new HeadCoach(teamEntity.getManager()));
        tDto.setWealth(BigDecimal.valueOf(teamEntity.getWealth()).setScale(2, RoundingMode.HALF_UP));
        tDto.setLeague(league);
        tDto.setStadium(stadiumConverter.getIntermediateEntityFromEntity(teamEntity.getStadiumEntity(), tDto));
        tDto.setCity(cityConverter.entityToDto(teamEntity.getCityEntity()));
        //------------------------
        tDto.setStartWealth(tDto.getWealth());
        tDto.setCoaches(new ArrayList<>());
        tDto.setLoans(new ArrayList<>());
        tDto.setStadiumExpenses(BigDecimal.ZERO);
        tDto.setPersonalExpenses(BigDecimal.ZERO);
        tDto.setMarketExpenses(BigDecimal.ZERO);
        tDto.setTransferExpenses(BigDecimal.ZERO);
        return tDto;
    }

    public TeamOnPagePlayersDto dtoToTeamOnPagePlayersDto(Team team){
        TeamOnPagePlayersDto tOnPageDto = new TeamOnPagePlayersDto();
        tOnPageDto.setName(team.getName());
        tOnPageDto.setWealth(team.getWealth().setScale(2, RoundingMode.HALF_UP));
        tOnPageDto.setTeamFullSize(team.getPlayerList().size());
        tOnPageDto.setPlayerParameter(-1);
        tOnPageDto.setSortParameter(0);
        return tOnPageDto;
    }


}
