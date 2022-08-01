package ru.sargassov.fmweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MarketDto {
    private String marketType;
    private BigDecimal oneWeekCost;
    private BigDecimal twoWeeksCost;
    private BigDecimal fourWeeksCost;
}
