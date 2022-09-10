package ru.sargassov.fmweb.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankDto {
    private long id;
    private String title;
    private String typeOfReturn;
    private BigDecimal percentDay;
    private BigDecimal percentWeek;
    private BigDecimal percentMonth;
    private BigDecimal fullLoanCoeff;
    private BigDecimal maxLoanAmount;
    private BigDecimal tookMoney;
}
