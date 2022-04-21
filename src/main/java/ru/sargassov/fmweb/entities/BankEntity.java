package ru.sargassov.fmweb.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "banks")
@Data
@NoArgsConstructor
public class BankEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "percent_day")
    private Integer percentDay;

    @Column(name = "percent_week")
    private Integer percentWeek;

    @Column(name = "percent_month")
    private Integer percentMonth;

    @Column(name = "full_loan_coeff")
    private Double fullLoanCoeff;

    @Column(name = "max_loan_amount")
    private Integer maxLoanAmount;
}