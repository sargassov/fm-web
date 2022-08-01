package ru.sargassov.fmweb.dto.player_dtos;

import lombok.Data;

@Data
public class CreatedPlayerDto extends PlayerHardSkillDto {
    protected int trainingAble;
    protected int number;

}
