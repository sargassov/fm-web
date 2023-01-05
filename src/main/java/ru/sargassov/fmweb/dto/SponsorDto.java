package ru.sargassov.fmweb.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SponsorDto {
    private Long id;
    private String name;
    private BigDecimal dayWage;
    private BigDecimal matchWage;
    private BigDecimal goalBonusWage;
    private BigDecimal winWage;
    private BigDecimal deuceWage;
    private BigDecimal contractBonusWage;
}
