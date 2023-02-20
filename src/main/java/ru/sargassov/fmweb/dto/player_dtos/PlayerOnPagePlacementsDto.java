package ru.sargassov.fmweb.dto.player_dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlayerOnPagePlacementsDto extends PlayerDto {
    private Integer place;
    private Integer number;
    private String position;
    private Integer power;

    public PlayerOnPagePlacementsDto(String name, Integer place, Integer number, String position, Integer power) {
        this.name = name;
        this.place = place;
        this.number = number;
        this.position = position;
        this.power = power;
    }
}
