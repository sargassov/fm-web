package ru.sargassov.fmweb.dto.days_dtos;

import lombok.Data;

import java.util.List;

@Data
public class EventDto {
    private String date;

    
    private Integer tour;
    private List<MatchDto> matches;
    private String passed;
    private String type;
    private String event;
}
