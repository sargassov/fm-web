package ru.sargassov.fmweb.dto.player_dtos;

import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerOnTrainingDto extends PlayerDto{
    private String onTraining;
    private String position;
    private Integer trainingAble;
    private Integer trainingBalance;
    private Integer tire;
}
