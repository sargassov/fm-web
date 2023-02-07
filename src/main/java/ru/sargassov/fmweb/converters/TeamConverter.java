package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.team_dtos.TeamOnPagePlayersDto;
import ru.sargassov.fmweb.entities.TeamEntity;
import ru.sargassov.fmweb.intermediate_entities.HeadCoach;
import ru.sargassov.fmweb.intermediate_entities.League;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_spi.HeadCoachIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.PlayerIntermediateServiceSpi2;
import ru.sargassov.fmweb.intermediate_spi.StadiumIntermediateServiceSpi;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

@Component
@AllArgsConstructor
public class TeamConverter {
    private final StadiumIntermediateServiceSpi stadiumIntermediateService;
    private final PlayerIntermediateServiceSpi2 playerIntermediateService2;
    private final HeadCoachIntermediateServiceSpi headCoachIntermediateService;
    private static final Integer START_PLAYER_PARAMETER = -1;
    private static final Integer START_SORT_PARAMETER = 0;
    private static final Integer BIG_DECIMAL_SCALE = 2;

    public Team getIntermediateEntityFromEntity(TeamEntity teamEntity, User user, League league){
        Team team = new Team();
        team.setUser(user);
        var headCoach = attractHeadCoach(teamEntity.getManager(), user);
        team.setTeamEntityId(teamEntity.getId());
        team.setName(teamEntity.getName());
        team.setHeadCoach(headCoach);
        team.setLeague(league);
        team.setWealth(BigDecimal.valueOf(teamEntity.getWealth()).setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP));
        team.setStartWealth(team.getWealth());
        team.setCoaches(new ArrayList<>());
        team.setLoans(new ArrayList<>());
        team.setMarkets(new ArrayList<>());
        team.setStadiumExpenses(BigDecimal.ZERO);
        team.setPersonalExpenses(BigDecimal.ZERO);
        team.setMarketExpenses(BigDecimal.ZERO);
        team.setTransferExpenses(BigDecimal.ZERO);
        var stadiumEntityId = teamEntity.getStadiumEntity().getId();
        var stadium = stadiumIntermediateService.findByStadiumEntityIdAndUser(stadiumEntityId, user);
        var city = stadium.getCity();
        team.setStadium(stadium);
        team.setCity(city);
        return team;
    }

    private HeadCoach attractHeadCoach(String manager, User user) {
        var headCoach = new HeadCoach();
        headCoach.setName(manager);
        headCoach.setUser(user);
        return headCoachIntermediateService.save(headCoach);
    }

    public TeamOnPagePlayersDto dtoToTeamOnPagePlayersDto(Team team) {
        TeamOnPagePlayersDto tOnPageDto = new TeamOnPagePlayersDto();
        tOnPageDto.setId(team.getId());
        tOnPageDto.setName(team.getName());
        tOnPageDto.setWealth(team.getWealth().setScale(2, RoundingMode.HALF_UP));
        tOnPageDto.setTeamFullSize(playerIntermediateService2.findByTeam(team).size());
        tOnPageDto.setPlayerParameter(START_PLAYER_PARAMETER);
        tOnPageDto.setSortParameter(START_SORT_PARAMETER);
        tOnPageDto.setStadium(team.getStadium().getTitle());
        return tOnPageDto;
    }


}
