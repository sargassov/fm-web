package ru.sargassov.fmweb.dto.player_dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatedPlayerDto extends PlayerHardSkillDto {
    protected int trainingAble;
    protected int number;

}
