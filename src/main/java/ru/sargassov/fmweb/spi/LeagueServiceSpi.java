package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.dto.LeagueDto;
import ru.sargassov.fmweb.dto.team_dtos.TeamResultDto;
import ru.sargassov.fmweb.intermediate_entites.League;

import java.util.List;

public interface LeagueServiceSpi {

   League getRussianLeague();

   LeagueDto getLeagueName();

    List<TeamResultDto> loadTeamTable();
}