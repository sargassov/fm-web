package ru.sargassov.fmweb.dto.player_dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class IdNamePricePlayerDto extends PlayerDto{
    private BigDecimal price;
}
