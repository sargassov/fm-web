package ru.sargassov.fmweb.intermediate_spi;

import lombok.NonNull;
import ru.sargassov.fmweb.dto.player_dtos.PlayerSoftSkillDto;

import java.util.List;

public interface TeamIntermediateServiceSpi2 {

    List<PlayerSoftSkillDto> getAllPlayersByUserTeam(@NonNull Integer parameter);
}
