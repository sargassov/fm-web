package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.League;
import ru.sargassov.fmweb.entities.LeagueEntity;

import java.util.ArrayList;

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
