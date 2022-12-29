package ru.sargassov.fmweb.dto.team_dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TeamOnPagePlayersDto extends TeamDto {
    private BigDecimal wealth;
    private Integer countParameter;
    private Integer playerParameter;
    private Integer teamFullSize;
    private Integer sortParameter;
    private String stadium;
}
