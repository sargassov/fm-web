package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.BankDto;
import ru.sargassov.fmweb.dto.LeagueDto;
import ru.sargassov.fmweb.dto.SponsorDto;
import ru.sargassov.fmweb.dto.TeamDto;
import ru.sargassov.fmweb.entities.League;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class LeagueConverter {
    private final LeagueDto leagueDto;

    public LeagueDto entityToDto(League league){
        leagueDto.setId(league.getId());
        leagueDto.setName(league.getName());
        leagueDto.setTeamList(new ArrayList<>());
        return leagueDto;
    }
}
