package ru.sargassov.fmweb.dto.player_dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlayerOnPagePlacementsDto extends PlayerDto {
    private Integer strategyPlace;
    private Integer number;
    private String position;
    private Integer power;

    public PlayerOnPagePlacementsDto(String name, Integer strategyPlace, Integer number, String position, Integer power) {
        this.name = name;
        this.strategyPlace = strategyPlace;
        this.number = number;
        this.position = position;
        this.power = power;
    }
}
