package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.team_dtos.TeamOnPagePlayersDto;
import ru.sargassov.fmweb.entities.TeamEntity;
import ru.sargassov.fmweb.intermediate_entities.HeadCoach;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_spi.HeadCoachIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.LeagueIntermediateServiceSpi;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

@Component
@AllArgsConstructor
public class TeamConverter {

    private final LeagueIntermediateServiceSpi leagueIntermediateService;
    private final StadiumConverter stadiumConverter;

    private final HeadCoachIntermediateServiceSpi headCoachIntermediateService;
    private final CityConverter cityConverter;

    public Team getIntermediateEntityFromEntity(TeamEntity teamEntity, User user){
        Team team = new Team();
        var headCoach = attractHeadCoach(teamEntity.getManager(), user);
        var league = leagueIntermediateService.getLeague(user);
        var entityStadium = teamEntity.getStadiumEntity();
        var stadium = stadiumConverter.getIntermediateEntityFromEntity(entityStadium, team, user, league);
        var city = stadium.getCity();

        team.setUser(user);
        team.setTeamEntityId(teamEntity.getId());
        team.setName(teamEntity.getName());
        team.setHeadCoach(headCoach);
        team.setLeague(league);
        team.setWealth(BigDecimal.valueOf(teamEntity.getWealth()).setScale(2, RoundingMode.HALF_UP));
        team.setStadium(stadium);
        team.setCity(city);
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

    private HeadCoach attractHeadCoach(String manager, User user) {
        var headCoach = new HeadCoach();
        headCoach.setName(manager);
        headCoach.setUser(user);
        return headCoachIntermediateService.save(headCoach);
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
