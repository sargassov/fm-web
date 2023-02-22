package ru.sargassov.fmweb.dto.player_dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PlayerSoftSkillDto extends CreatedPlayerDto {
    private String club;
    private Long teamId;
    private boolean isInjury;
    private int strategyPlace;
    private int power;
    private String captain;
    private int tire;
    private int timeBeforeTreat;
    private BigDecimal price;
    private int number;
}
