package ru.sargassov.fmweb.dto.team_dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamResultDto extends TeamDto {
    private String num;
    private String games;
    private String won;
    private String drawn;
    private String lost;
    private String goalScored;
    private String goalMissed;
    private String points;
}
