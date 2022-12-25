package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.dto.player_dtos.PlayerOnTrainingDto;
import ru.sargassov.fmweb.dto.team_dtos.TeamOnPagePlayersDto;
import ru.sargassov.fmweb.entities.TeamEntity;
import ru.sargassov.fmweb.intermediate_entities.Coach;
import ru.sargassov.fmweb.intermediate_entities.HeadCoach;
import ru.sargassov.fmweb.intermediate_entities.League;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_spi.HeadCoachIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.LeagueIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.StadiumIntermediateServiceSpi;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TeamConverter {

    private final StadiumIntermediateServiceSpi stadiumIntermediateService;
    private final HeadCoachIntermediateServiceSpi headCoachIntermediateService;

    public Team getIntermediateEntityFromEntity(TeamEntity teamEntity, User user, League league){
        Team team = new Team();
        team.setUser(user);
        var headCoach = attractHeadCoach(teamEntity.getManager(), user);
        team.setTeamEntityId(teamEntity.getId());
        team.setName(teamEntity.getName());
        team.setHeadCoach(headCoach);
        team.setLeague(league);
        team.setWealth(BigDecimal.valueOf(teamEntity.getWealth()).setScale(2, RoundingMode.HALF_UP));
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
