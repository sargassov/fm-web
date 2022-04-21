package ru.sargassov.fmweb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.GregorianCalendar;

@Data
@NoArgsConstructor
public class Bank {
    private long id;
    private String title;
    private int percentDay;
    private int percentWeek;
    private int percentMonth;
    private double fullLoanCoeff;
    private int maxLoanAmount;
    //=============================//
    private GregorianCalendar dateOfLoan;
    private GregorianCalendar remainsDate;
    private TypeOfReturn typeOfReturn;
    private int payPerDay;
    private int payPerWeek;
    private int payPerMonth;
    private double fullLoanAmountValue;
    private long tookMoney;
    private long remainMoney;
    private long alreadyPaid;

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