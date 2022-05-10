package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.intermediate_entites.League;
import ru.sargassov.fmweb.entities.LeagueEntity;

@Component
@AllArgsConstructor
public class LeagueConverter {
    private final League league;

    public League entityToDto(LeagueEntity leagueEntity){
        league.setId(leagueEntity.getId());
        league.setName(leagueEntity.getName());
        return league;
    }
}
