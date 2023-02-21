package ru.sargassov.fmweb.intermediate_spi;

import lombok.NonNull;
import ru.sargassov.fmweb.dto.player_dtos.PlayerSoftSkillDto;
import ru.sargassov.fmweb.intermediate_entities.Team;

import java.util.List;

public interface TeamIntermediateServiceSpi2 {

    List<PlayerSoftSkillDto> getAllPlayersByUserTeam(@NonNull Integer parameter);

    Team getById(Long teamId);

    Team save(Team team);
}
