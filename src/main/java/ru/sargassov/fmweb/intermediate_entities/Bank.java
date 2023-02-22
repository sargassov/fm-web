package ru.sargassov.fmweb.intermediate_entities;

import lombok.*;
import ru.sargassov.fmweb.constants.BaseUserEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "bank")
@Getter
@Setter
@RequiredArgsConstructor
public class Bank extends BaseUserEntity {

    @ManyToOne
    @JoinColumn(name = "id_team")
    private Team team;

    @Column(name = "title")
    private String title;

    @Column(name = "percent_day")
    private Integer percentDay;

    @Column(name = "percent_week")
    private Integer percentWeek;

    @Column(name = "percent_month")
    private Integer percentMonth;

    @Column(name = "fill_loan_coeff")
    private Double fullLoanCoeff;

    @Column(name = "max_loan_amount")
    private BigDecimal maxLoanAmount;
    @ManyToOne
    @JoinColumn(name = "id_day_of_loan")
    private Day dateOfLoan;
    @ManyToOne
    @JoinColumn(name = "id_remains_day")
    private Day remainsDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_return")
    private TypeOfReturn typeOfReturn;

    @Column(name = "pay_per_day")
    private BigDecimal payPerDay;
    @Column(name = "pay_per_week")
    private BigDecimal payPerWeek;

    @Column(name = "pay_per_month")
    private BigDecimal payPerMonth;

    @Column(name = "full_loan_amount_value")
    private Double fullLoanAmountValue;

    @Column(name = "took_money")
    private BigDecimal tookMoney;

    @Column(name = "remain_money")
    private BigDecimal remainMoney;

    @Column(name = "already_paid")
    private BigDecimal alreadyPaid;

    @Column(name = "active")
    private boolean active = true;

    public static TypeOfReturn guessTypeOfReturn(String typeOfReturn) {
        if(typeOfReturn.equals("PER_DAY")) return TypeOfReturn.PER_DAY;
        else if(typeOfReturn.equals("PER_WEEK")) return TypeOfReturn.PER_WEEK;
        else return TypeOfReturn.PER_MONTH;
    }

    public void remainLoan() {
        dateOfLoan = null;
        remainsDate = null;
        payPerDay = null;
        payPerWeek = null;
        payPerMonth = null;
        fullLoanAmountValue = 0.0;
        tookMoney = null;
        remainMoney = null;
        alreadyPaid = null;
        active = true;
    }

    public enum TypeOfReturn {PER_DAY, PER_WEEK, PER_MONTH};

//    private final String name;
//    private GregorianCalendar dateOfLoan;
//    private GregorianCalendar remainsDate;
//    private TypeOfReturn typeOfReturn;
//
//    private final int percentValueDay;
//    private final int percentValueWeek;
//    private final int percentValueMonth;
//
//    private int payPerDay;
//    private int payPerWeek;
//    private int payPerMonth;
//    private double fullLoanAmountValue;
//    private long tookMoney;
//    private long remainMoney;
//    private long alreadyPaid = 0L;
//    private final long maxLoanValue;
//
//
//    public enum TypeOfReturn {PER_DAY, PER_WEEK, PER_MONTH};

//    public Bank(String info){
//        String[] mass = info.split("/");
//        this.name = mass[0];
//        this.percentValueDay = Integer.parseInt(mass[1]);
//        this.percentValueWeek = Integer.parseInt(mass[2]);
//        this.percentValueMonth = Integer.parseInt(mass[3]);
//        this.fullLoanAmountValue = Double.parseDouble(mass[4]);
//        this.maxLoanValue = (Long.parseLong(mass[5]) * 1_000_000);
//        //++++++++++++++++++++++++++++++++++++++++++//
//        System.out.println("BANKNAME = " + name);
//        //++++++++++++++++++++++++++++++++++++++++++//
//
//    }
//
//    public void returnLoan(){
//        //if(typeOfReturn.equals(TypeOfReturn.PER_DAY)) rfpl.myTeam.
//
//        league.getUserTeam().setWealth(league.getUserTeam().getWealth() - remainMoney);
//        tookMoney = 0;
//        remainMoney = 0;
//        alreadyPaid = 0;
//        typeOfReturn = null;
//        dateOfLoan = null;
//        remainsDate = null;
//        payPerDay = 0;
//        payPerWeek = 0;
//        payPerMonth = 0;
//        league.getUserTeam().getLoans().remove(this);
//        league.addToBankList(this);
//        //System.out.println("max value of loans = " + rfpl.myTeam.maxValueOfLoans);
//        //rfpl.myTeam.maxValueOfLoans;
//        System.out.println("Loan of " + name + " bank was paid.");
//
//    }
//
//    public long getPayPerDay() {
//        return payPerDay;
//    }
//
//    public void setPayPerDay(int payPerDay) {
//        this.payPerDay = payPerDay;
//    }
//
//    public int getPayPerWeek() {
//        return payPerWeek;
//    }
//
//    public void setPayPerWeek(int payPerWeek) {
//        this.payPerWeek = payPerWeek;
//    }
//
//    public int getPayPerMonth() {
//        return payPerMonth;
//    }
//
//    public void setPayPerMonth(int payPerMonth) {
//        this.payPerMonth = payPerMonth;
//    }
//
//    public void setAlreadyPaid(long alreadyPaid) {
//        this.alreadyPaid = alreadyPaid;
//    }
//
//
//    public long getRemainMoney() {
//        return remainMoney;
//    }
//
//    public void setRemainMoney(long remainMoney) {
//        this.remainMoney = remainMoney;
//    }
//
//    public void setTookMoney(int tookMoney) {
//        this.tookMoney = tookMoney;
//    }
//
//    public long getTookMoney() {
//        return tookMoney;
//    }
//
//    public void setRemainsDate(GregorianCalendar remainsDate) { this.remainsDate = remainsDate; }
//
//    public GregorianCalendar getRemainsDate() { return remainsDate; }
//
//    public GregorianCalendar getDateOfLoan() {
//        return dateOfLoan;
//    }
//
//    public void setTypeOfReturn(TypeOfReturn typeOfReturn) { this.typeOfReturn = typeOfReturn; }
//
//    public void setDateOfLoan(GregorianCalendar dateOfLoan) { this.dateOfLoan = dateOfLoan; }
//
//    public TypeOfReturn getTypeOfReturn() { return typeOfReturn; }
//
//    public long getAlreadyPaid() { return alreadyPaid; }
//
//    public double getFullVal() {
//        return fullLoanAmountValue;
//    }
//
//    public long getMaxLoan() {
//        return maxLoanValue;
//    }
//
//    public int getPercentDay() {
//        return percentValueDay;
//    }
//
//    public int getPercentMon() {
//        return percentValueMonth;
//    }
//
//    public int getPercentWeek() {
//        return percentValueWeek;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//
//
//    public void setLeague(League league) {
//        this.league = league;
//    }


}