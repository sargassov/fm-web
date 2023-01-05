package ru.sargassov.fmweb.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanDto {
    private Long id;
    private String title;
    private BigDecimal amount;
    private String tor;
    private String loanDate;
    private String remainDate;
    private BigDecimal alreadyPaid;
    private BigDecimal remainsToPay;
}
