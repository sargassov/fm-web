package ru.sargassov.fmweb.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlacementOnPagePlacementsDto {
    private String title;
    private List<PlayerOnPagePlacementsDto> players;
    private Integer currentTeamPower;
    private Integer size;
}
