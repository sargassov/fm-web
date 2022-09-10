package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.LeagueDto;
import ru.sargassov.fmweb.intermediate_entities.League;
import ru.sargassov.fmweb.entities.LeagueEntity;
import ru.sargassov.fmweb.intermediate_entities.User;

@Component
@AllArgsConstructor
public class LeagueConverter {

    public League getIntermediateEntityFromEntity(LeagueEntity leagueEntity, User user){
        var league = new League();
        league.setName(leagueEntity.getName());
        league.setUser(user);
        return league;
    }

    public LeagueDto getLeagueDtoFromIntermediateEntity() {
        LeagueDto leagueDto = new LeagueDto();
        leagueDto.setName(league.getName());
        return leagueDto;
    }
}
