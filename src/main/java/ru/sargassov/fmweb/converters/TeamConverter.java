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
        Team team = new Team();
        team.setId(teamEntity.getId());
        team.setName(teamEntity.getName());
        team.setHeadCoach(new HeadCoach(teamEntity.getManager()));
        team.setWealth(BigDecimal.valueOf(teamEntity.getWealth()).setScale(2, RoundingMode.HALF_UP));
        team.setLeague(league);
        team.setStadium(stadiumConverter.getIntermediateEntityFromEntity(teamEntity.getStadiumEntity(), team));
        team.setCity(cityConverter.entityToDto(teamEntity.getCityEntity()));
        //------------------------
        team.setStartWealth(team.getWealth());
        team.setCoaches(new ArrayList<>());
        team.setLoans(new ArrayList<>());
        team.setMarkets(new ArrayList<>());
        team.setStadiumExpenses(BigDecimal.ZERO);
        team.setPersonalExpenses(BigDecimal.ZERO);
        team.setMarketExpenses(BigDecimal.ZERO);
        team.setTransferExpenses(BigDecimal.ZERO);
        return team;
    }

    public TeamOnPagePlayersDto dtoToTeamOnPagePlayersDto(Team team){
        TeamOnPagePlayersDto tOnPageDto = new TeamOnPagePlayersDto();
        tOnPageDto.setName(team.getName());
        tOnPageDto.setWealth(team.getWealth().setScale(2, RoundingMode.HALF_UP));
        tOnPageDto.setTeamFullSize(team.getPlayerList().size());
        tOnPageDto.setPlayerParameter(-1);
        tOnPageDto.setSortParameter(0);
        tOnPageDto.setStadium(team.getStadium().getTitle());
        return tOnPageDto;
    }


}
