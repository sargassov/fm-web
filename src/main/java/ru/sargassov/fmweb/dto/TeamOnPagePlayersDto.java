package ru.sargassov.fmweb.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TeamOnPagePlayersDto {
    private String name;
    private BigDecimal wealth;
    private Integer parameter;
    private Integer playerParameter;
    private Integer teamFullSize;
    private Integer sortParameter;
}
