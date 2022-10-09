package ru.sargassov.fmweb.intermediate_entities;

import lombok.*;
import ru.sargassov.fmweb.constants.BaseUserEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "sponsor")
@Getter
@Setter
@RequiredArgsConstructor
public class Sponsor extends BaseUserEntity {


    @Column(name = "name")
    private String name;

    @Column(name = "id_sponsor_entity")
    private Long sponsorEntityId;

    @Column(name = "day_wage")
    private BigDecimal dayWage;

    @Column(name = "match_wage")
    private BigDecimal matchWage;

    @Column(name = "goal_bonus_wage")
    private BigDecimal goalBonusWage;

    @Column(name = "win_wage")
    private BigDecimal winWage;

    @Column(name = "deuce_wage")
    private BigDecimal deuceWage;

    @Column(name = "contract_bonus_wage")
    private BigDecimal contractBonusWage;
    //---------------------------

    public void signContractWithClub(Team t){
        t.setWealth(t.getWealth().add(contractBonusWage));
    }

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