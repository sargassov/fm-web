package ru.sargassov.fmweb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
public class SponsorDto {
    private long id;
    private String name;
    private int dayWage;
    private int matchWage;
    private int goalBonusWage;
    private int winWage;
    private int deuceWage;
    private int contractBonusWage;
    //---------------------------

    //    private String name;
//
//    private long dayWage;
//    private long matchWage;
//    private long goalBonusWage;
//    private long winWage;
//    private long deuceWage;
//    private long contractBonusWage;
//    private static int mltplCoeff = 1_000_000;
//
//    public Sponsor(String line) {
//        String[] lineMass = line.split("/");
//        name = lineMass[0];
//        dayWage = Long.parseLong(lineMass[1]);
//        matchWage = Long.parseLong(lineMass[2]);
//        goalBonusWage = Long.parseLong(lineMass[3]);
//        winWage = Long.parseLong(lineMass[4]);
//        deuceWage = Long.parseLong(lineMass[5]);
//        contractBonusWage = Long.parseLong(lineMass[6]);
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public long getDayWage() {
//        return dayWage;
//    }
//
//    public void setDayWage(long dayWage) {
//        this.dayWage = dayWage;
//    }
//
//    public long getMatchWage() {
//        return matchWage;
//    }
//
//    public void setMatchWage(long matchWage) {
//        this.matchWage = matchWage;
//    }
//
//    public long getGoalBonusWage() {
//        return goalBonusWage;
//    }
//
//    public void setGoalBonusWage(long goalBonusWage) {
//        this.goalBonusWage = goalBonusWage;
//    }
//
//    public long getWinWage() {
//        return winWage;
//    }
//
//    public void setWinWage(long winWage) {
//        this.winWage = winWage;
//    }
//
//    public long getDeuceWage() {
//        return deuceWage;
//    }
//
//    public void setDeuceWage(long deuceWage) {
//        this.deuceWage = deuceWage;
//    }
//
//    public long getContractBonusWage() {
//        return contractBonusWage;
//    }
//
//    public void setContractBonusWage(long contractBonusWage) {
//        this.contractBonusWage = contractBonusWage;
//    }
}