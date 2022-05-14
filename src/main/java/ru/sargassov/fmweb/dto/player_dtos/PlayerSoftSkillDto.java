package ru.sargassov.fmweb.dto.player_dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlayerSoftSkillDto extends CreatedPlayerDto {
    private String club;
    private boolean isInjury;
    private int strategyPlace;
    private int power;
    private String captain;
    private int tire;
    private int timeBeforeTreat;
    private BigDecimal price;
    private int number;
}
