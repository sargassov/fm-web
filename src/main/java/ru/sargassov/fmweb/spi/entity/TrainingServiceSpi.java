package ru.sargassov.fmweb.spi.entity;

import ru.sargassov.fmweb.dto.player_dtos.PlayerOnTrainingDto;

import java.util.List;

public interface TrainingServiceSpi {

    List<PlayerOnTrainingDto> getAllPlayersOnTrainingByUserTeam(Integer parameter);
}
