package ru.sargassov.fmweb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
public class CityDto {
    private Long id;
    private String name;
    private LeagueDto league;
}