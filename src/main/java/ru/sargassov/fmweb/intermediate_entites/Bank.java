package ru.sargassov.fmweb.intermediate_entites;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sargassov.fmweb.dto.BankDto;
import ru.sargassov.fmweb.dto.FinalPayment;
import ru.sargassov.fmweb.intermediate_entites.days.Day;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class Bank {
    private long id;
    private String title;
    private int percentDay;
    private int percentWeek;
    private int percentMonth;
    private double fullLoanCoeff;
    private BigDecimal maxLoanAmount;
    //=============================//
    private Day dateOfLoan;
    private Day remainsDate;
    private TypeOfReturn typeOfReturn;
    private BigDecimal payPerDay;
    private BigDecimal payPerWeek;
    private BigDecimal payPerMonth;
    private double fullLoanAmountValue;
    private BigDecimal tookMoney;
    private BigDecimal remainMoney;
    private BigDecimal alreadyPaid;
    private boolean active = true;

    public static TypeOfReturn guessTypeOfReturn(String typeOfReturn) {
        if(typeOfReturn.equals("PER_DAY")) return TypeOfReturn.PER_DAY;
        else if(typeOfReturn.equals("PER_WEEK")) return TypeOfReturn.PER_WEEK;
        else return TypeOfReturn.PER_MONTH;
    }

    public static Day guessRemainsDate(Day dateOfLoan, BankDto loan) {
        Day remainsDay = new Day();
        LocalDate startLocalDate = dateOfLoan.getDate();

        if(loan.getTypeOfReturn().equals("PER_DAY")){
            int valueDays = loan.getFullLoanCoeff().divide(loan.getPercentDay(), RoundingMode.HALF_UP).intValue() + 1;
            remainsDay.setDate(startLocalDate.plusDays(valueDays));
        } else if (loan.getTypeOfReturn().equals("PER_WEEK")) {
            int valueWeeks = loan.getFullLoanCoeff().divide(loan.getPercentWeek(), RoundingMode.HALF_UP).intValue() + 1;
            remainsDay.setDate(startLocalDate.plusWeeks(valueWeeks));
        } else {
            int valueMonths = loan.getFullLoanCoeff().divide(loan.getPercentMonth(), RoundingMode.HALF_UP).intValue() + 1;
            remainsDay.setDate(startLocalDate.plusMonths(valueMonths));
        }

        return remainsDay;
    }

    public List<String> paymentPeriod(TypeOfReturn type, Team team, FinalPayment finalPayment) {
        List<String> notesOfChanges = new ArrayList<>();
        BigDecimal payType;

        switch (type) {
            case PER_DAY:
                payType = payPerDay;
                break;
            case PER_WEEK:
                payType = payPerWeek;
                break;
            default:
                payType = payPerMonth;
        }

        if(remainMoney.compareTo(payType) < 0){
            finalPayment.setFinal(true);
            lastPayment(team);
            notesOfChanges.add(payType + " Euro was paid to the Bank " + title + ". It is " + type);
            notesOfChanges.add(title + " is closed!");
            return notesOfChanges;
        }

        BigDecimal wealth = team.getWealth();
        wealth = wealth.add(payType);
        alreadyPaid = alreadyPaid.add(payType);
        remainMoney = remainMoney.subtract(payType);
        notesOfChanges.add(payType + " Euro was paid to the Bank " + title + ". It is " + type);
        return notesOfChanges;
    }

    private void lastPayment(Team team) {
        BigDecimal wealth = team.getWealth();
        wealth = wealth.subtract(remainMoney);
        remainMoney = BigDecimal.ZERO;
        team.getLoans().remove(this);
        remainLoan();
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