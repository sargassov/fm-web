package ru.sargassov.fmweb.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TeamOnPagePlayersDto {
    String name;
    BigDecimal wealth;
}
