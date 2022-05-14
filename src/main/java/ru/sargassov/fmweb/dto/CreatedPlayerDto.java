package ru.sargassov.fmweb.dto;

import lombok.Data;
import ru.sargassov.fmweb.converters.PlayerPriceSetter;
import ru.sargassov.fmweb.intermediate_entites.Player;

import java.math.BigDecimal;

@Data
public class CreatedPlayerDto {
    private String name;
    private String natio;
    private String position;
    private int gkAble;
    private int defAble;
    private int midAble;
    private int forwAble;
    private int captainAble;
    private int trainingAble;
    private int birthYear;
    private int number;

}
