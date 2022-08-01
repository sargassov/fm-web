package ru.sargassov.fmweb.dto.team_dtos;

import lombok.Data;

@Data
public class TeamTransInformationDto {
    private Integer playerParameter;
    private Integer sortParameter;
    private Integer delta;
}