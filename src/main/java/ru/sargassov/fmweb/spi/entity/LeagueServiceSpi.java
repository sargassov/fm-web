package ru.sargassov.fmweb.spi.entity;

import ru.sargassov.fmweb.dto.LeagueDto;
import ru.sargassov.fmweb.dto.matrix_dto.CortageDto;
import ru.sargassov.fmweb.dto.team_dtos.TeamResultDto;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

public interface LeagueServiceSpi {

   void getRussianLeague(User user);

   LeagueDto getLeagueName();
   List<TeamResultDto> loadTeamTable();

   List<CortageDto> loadResultMatrix();
}
