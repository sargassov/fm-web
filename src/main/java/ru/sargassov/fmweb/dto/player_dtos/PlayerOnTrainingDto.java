package ru.sargassov.fmweb.dto.player_dtos;

import lombok.Data;

@Data
public class PlayerOnTrainingDto extends PlayerDto{
    private String onTraining;
    private String position;
    private Integer trainingAble;
    private Integer trainingBalance;
    private Integer tire;
}
