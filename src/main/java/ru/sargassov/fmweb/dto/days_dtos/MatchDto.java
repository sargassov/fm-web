package ru.sargassov.fmweb.dto.days_dtos;

import lombok.Data;

@Data
public class MatchDto {
    private String homeTeam;
    private String awayTeam;
    private String stadium;
    private String score;
    private String goals;
}
