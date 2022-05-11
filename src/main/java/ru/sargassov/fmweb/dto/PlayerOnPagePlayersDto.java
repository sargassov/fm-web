package ru.sargassov.fmweb.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlayerOnPagePlayersDto {
    private long id;
    private String name;
    private String club;
    private String natio;
    private String position;
    private int gkAble;
    private int defAble;
    private int midAble;
    private int forwAble;
    private int captainAble;
    private boolean isInjury;
    private int trainingAble;
    private int birthYear;
    private int strategyPlace;
    private int power;
    private int tire;
    private int timeBeforeTreat;
    private BigDecimal price;
    private int number;
}
