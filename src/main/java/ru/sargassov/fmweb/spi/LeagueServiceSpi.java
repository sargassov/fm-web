package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.dto.LeagueDto;
import ru.sargassov.fmweb.dto.matrix_dto.CortageDto;
import ru.sargassov.fmweb.dto.player_dtos.PlayerSoftSkillDto;
import ru.sargassov.fmweb.dto.team_dtos.TeamResultDto;
import ru.sargassov.fmweb.intermediate_entites.League;

import java.util.List;

public interface LeagueServiceSpi {

   League getRussianLeague();

   LeagueDto getLeagueName();

    List<TeamResultDto> loadTeamTable();

    List<CortageDto> loadResultMatrix();

    List<PlayerSoftSkillDto> loadPlayersSort(Integer parameter);
}
