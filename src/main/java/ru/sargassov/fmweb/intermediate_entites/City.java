package ru.sargassov.fmweb.intermediate_entites;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class City {
    private Long id;
    private String name;
    private League league;
}