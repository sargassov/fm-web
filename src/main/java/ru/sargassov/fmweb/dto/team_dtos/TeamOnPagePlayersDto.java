package ru.sargassov.fmweb.dto.team_dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TeamOnPagePlayersDto {
    private String name;
    private BigDecimal wealth;
    private Integer countParameter;
    private Integer playerParameter;
    private Integer teamFullSize;
    private Integer sortParameter;
    private String stadium;
}
