package ru.sargassov.fmweb.dto.player_dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class IdNamePricePlayerDto extends PlayerDto{
    private BigDecimal price;
}
