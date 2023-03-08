package ru.sargassov.fmweb.spi.entity;

import ru.sargassov.fmweb.dto.player_dtos.IdNamePricePlayerDto;
import ru.sargassov.fmweb.dto.player_dtos.PlayerSoftSkillDto;
import ru.sargassov.fmweb.dto.team_dtos.TeamOnPagePlayersDto;
import ru.sargassov.fmweb.intermediate_entities.Team;

import java.util.List;

public interface TransferServiceSpi {
    TeamOnPagePlayersDto getNameOfOpponentTeam(Integer parameter, Integer delta);

    List<PlayerSoftSkillDto> getTenPlayersFromNextTeam(Integer playerParameter, Integer sortParameter, Team team);

    List<PlayerSoftSkillDto> getTenPlayersFromNextTeam(Integer deltaPlayerParameter, Integer sortParameter, Long teamId);

    void buyNewPlayer(PlayerSoftSkillDto playerSoftSkillDto);

    List<IdNamePricePlayerDto> getSellingList();

    void sellPlayer(Long id);
}
