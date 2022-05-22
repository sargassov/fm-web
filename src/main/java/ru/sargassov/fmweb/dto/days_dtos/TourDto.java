package ru.sargassov.fmweb.dto.days_dtos;

import lombok.Data;

import java.util.List;

@Data
public class TourDto{
    private String date;
    private Integer tour;
    private List<MatchDto> matches;
    private Boolean passed;
}
