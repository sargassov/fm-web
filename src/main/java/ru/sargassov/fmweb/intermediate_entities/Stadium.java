package ru.sargassov.fmweb.intermediate_entities;

import lombok.*;
import ru.sargassov.fmweb.constants.BaseUserEntity;
import ru.sargassov.fmweb.constants.StadiumAnalytics;
import ru.sargassov.fmweb.exceptions.StadiumException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "stadium")
@Getter
@Setter
@NoArgsConstructor
public class Stadium extends BaseUserEntity {

    @OneToOne
    @JoinColumn(name = "id_team")
    private Team team;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "id_league")
    private League league;

    @ManyToOne
    @JoinColumn(name = "id_city")
    private City city;

    @Column(name = "full_capacity")
    private int fullCapacity;

    @Column(name = "vip_capacity")
    private int vipCapacity;

    @Column(name = "family_capacity")
    private int familyCapacity;

    @Column(name = "fan_capacity")
    private int fanCapacity;

    @Column(name = "simple_capacity")
    private int simpleCapacity;

    @Column(name = "away_capacity")
    private int awayCapacity;

    @Column(name = "usual_average_capacity")
    private int usualAverageCapacity;

    @Column(name = "vip_ticket_cost")
    private BigDecimal vipTicketCost;

    @Column(name = "family_ticket_cost")
    private BigDecimal familyTicketCost;

    @Column(name = "fan_ticket_cost")
    private BigDecimal fanTicketCost;

    @Column(name = "simple_ticket_cost")
    private BigDecimal simpleTicketCost;

    @Column(name = "away_ticket_cost")
    private BigDecimal awayTicketCost;

    @Column(name = "id_stadium_entity")
    private Long stadiumEntityId;

    public BigDecimal getMatchTicketRevenue() {

        BigDecimal[] ticketCosts = {
                vipTicketCost,
                familyTicketCost,
                fanTicketCost,
                simpleTicketCost,
                awayTicketCost
        };

        BigDecimal[] ticketValues = {
                BigDecimal.valueOf(vipCapacity),
                BigDecimal.valueOf(familyCapacity),
                BigDecimal.valueOf(fanCapacity),
                BigDecimal.valueOf(simpleCapacity),
                BigDecimal.valueOf(awayCapacity)
        };
        BigDecimal result = BigDecimal.ZERO;

        for(int i = 0; i < ticketCosts.length; i++){
            result = result.add(ticketCosts[i].multiply(ticketValues[i]));
        }
        return result;
    }

    public int getFullSectorCapacity() {
        List<Integer> capacities = new ArrayList<>(List.of(
                vipCapacity,
                familyCapacity,
                fanCapacity,
                simpleCapacity,
                awayCapacity
        ));

        int allSectorCapacity = 0;
        for(Integer i : capacities){
            allSectorCapacity += i;
        }
        return  allSectorCapacity;
    }

    public BigDecimal getCostByTypeOfSector(String type) {
        return StadiumAnalytics.getCostByTypeOfSector(this, type);
    }

    public void setTicketCostByTypeOfSector(String type, BigDecimal typeTicketCost) {
        if (type.startsWith("Simple")) {
            simpleTicketCost = typeTicketCost;
        } else if (type.startsWith("Family")) {
            familyTicketCost = typeTicketCost;
        } else if (type.startsWith("Fan")) {
            fanTicketCost = typeTicketCost;
        } else if (type.startsWith("VIP")) {
            vipTicketCost = typeTicketCost;
        } else if (type.startsWith("Away")) {
            awayTicketCost = typeTicketCost;
        } else {
            throw new StadiumException("Using unavailable type");
        }
    }

    public boolean approveExtense(String sectorType, int delta) {
        if (fullCapacity - getFullSectorCapacity() - delta < 0) {
            return false;
        }

        if (fullCapacity - getFullSectorCapacity() - delta > fullCapacity) {
            return false;
        }

        int currentSectorSize;
        if (sectorType.startsWith("Simple")) {
            currentSectorSize = simpleCapacity + delta;
        } else if (sectorType.startsWith("Family")) {
            currentSectorSize = familyCapacity + delta;
        } else if (sectorType.startsWith("Fan")) {
            currentSectorSize = fanCapacity + delta;
        } else if (sectorType.startsWith("VIP")) {
            currentSectorSize = vipCapacity + delta;
        } else if (sectorType.startsWith("Away")) {
            currentSectorSize = awayCapacity + delta;
        } else {
            throw new StadiumException("Using unavaliable type");
        }

        if (currentSectorSize < 0) {
            return false;
        }

        if (team.getWealth().compareTo(BigDecimal.ONE) <= 0) {
            return false;
        }

        return true;
    }

    public void makeExtension(String description, int delta) {
        if (description.startsWith("Simple")) {
            simpleCapacity += delta;
        } else if (description.startsWith("Family")) {
            familyCapacity += delta;
        } else if (description.startsWith("Fan")) {
            fanCapacity += delta;
        } else if (description.startsWith("VIP")) {
            vipCapacity += delta;
        } else if (description.startsWith("Away")) {
            awayCapacity += delta;
        } else {
            throw new StadiumException("Using unavaliable type");
        }
    }

    public void expand(Integer delta) {
        if (fullCapacity + delta < 0) {
            throw new StadiumException("the number of viewers should not be zero");
        }
        fullCapacity = fullCapacity + delta;
        Team team = this.team;
        team.setWealth(team.getWealth().subtract(BigDecimal.ONE));
    }


    //    List<Integer> tempCapacityArr;
//    List<Integer> tempCostArr;
//

////    private int capacityStad;
//    private double stadiumExpenses;
//
//
//    public Stadium(String title, int fullCapacity){
//        this.fullCapacity = fullCapacity;
//        this.title = title;
//        stadiumExpenses = 0.0;
//
//        usualAverageCapacity = 10000;
//
//        simpleCapacity = usualAverageCapacity;
//        vipCapacity = 0;
//        fanCapacity = 0;
//        familyCapacity = 0;
//        awayCapacity = 0;
//
//        simpleTicketCost = 40;
//        vipTicketCost = 0;
//        fanTicketCost = 0;
//        awayTicketCost = 0;
//        familyTicketCost = 0;
//
//        capacityAndCostArraysCreator();
//    }
//
//    private void capacityAndCostArraysCreator(){
//        tempCapacityArr = new ArrayList<>(Arrays.asList(simpleCapacity, familyCapacity,
//                fanCapacity, awayCapacity, vipCapacity));
//
//        tempCostArr = new ArrayList<>(Arrays.asList(simpleTicketCost, familyTicketCost,
//                fanTicketCost, awayTicketCost, vipTicketCost));
//    }
//
//    public int allTicketCost(){
//
//        List<Integer> allcost = new ArrayList<>(Arrays.asList(
//                simpleTicketCost * simpleCapacity,
//                vipTicketCost * vipCapacity,
//                fanTicketCost * fanCapacity,
//                awayTicketCost * awayCapacity,
//                familyTicketCost * familyCapacity));
//
//        int result = 0;
//        for(int a: allcost){
//            result += a;
//        }
//        return result;
//    }
//
//
//
//    public double matchTicketRevenueAmount() { // тот же метод что и allTicketCost только делить на миллион
//        double ticketAmount = 0.0;
//
//        for(int i = 0; i < tempCapacityArr.size(); i++){
//            ticketAmount += tempCapacityArr.get(i) * tempCostArr.get(i);
//        }
//
//        return ticketAmount / 1000000;
//    }
//
//    public int getFullSectorCapacity() {
//
//        int fullSectorCapacity = 0;
//
//        for(int i : tempCapacityArr){
//            fullSectorCapacity += i;
//        }
//
//        return fullSectorCapacity;
//    }
//
//    public int getFullNotSectorCapacity() {
//
//        return fullCapacity - getFullSectorCapacity();
//    }
//
//    public void setSimpleTicketCost(Integer simpleTicketCost) {
//        this.simpleTicketCost = simpleTicketCost;
//    }
//
//    public void setFamilyTicketCost(Integer familyTicketCost) {
//        this.familyTicketCost = familyTicketCost;
//    }
//
//    public void setFanTicketCost(Integer fanTicketCost) {
//        this.fanTicketCost = fanTicketCost;
//    }
//
//    public void setAwayTicketCost(Integer awayTicketCost) {
//        this.awayTicketCost = awayTicketCost;
//    }
//
//    public void setSimpleCapacity(Integer simpleCapacity) { this.simpleCapacity = simpleCapacity; }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public void setVipTicketCost(int vipTicketCost) {
//        this.vipTicketCost = vipTicketCost;
//    }
//
//    public void setVipCapacity(int vipCapacity) {
//        this.vipCapacity = vipCapacity;
//    }
//
//    public void setUsualAverageCapacity(int usualAverageCapacity) {
//        this.usualAverageCapacity = usualAverageCapacity;
//    }
//
//    public void setFullCapacity(int fullCapacity) {
//        this.fullCapacity = fullCapacity;
//    }
//
//    public void setFanCapacity(int fanCapacity) {
//        this.fanCapacity = fanCapacity;
//    }
//
//    public void setFamilyCapacity(int familyCapacity) {
//        this.familyCapacity = familyCapacity;
//    }
//
//    public void setAwayCapacity(int awayCapacity) {
//        this.awayCapacity = awayCapacity;
//    }
//
//    public int getVipTicketCost() {
//        return vipTicketCost;
//    }
//
//    public int getUsualAverageCapacity() {
//        return usualAverageCapacity;
//    }
//
//    public int getSimpleTicketCost() {
//        return simpleTicketCost;
//    }
//
//    public int getFanTicketCost() {
//        return fanTicketCost;
//    }
//
//    public int getFamilyTicketCost() {
//        return familyTicketCost;
//    }
//
//    public int getAwayTicketCost() {
//        return awayTicketCost;
//    }
//
//    public int getFullCapacity() {
//        return fullCapacity;
//    }
//
//    public double getStadiumExpenses() {
//        return stadiumExpenses;
//    }
//
//    public int getAwayCapacity() {
//        return awayCapacity;
//    }
//
//    public int getFamilyCapacity() {
//        return familyCapacity;
//    }
//
//    public int getFanCapacity() {
//        return fanCapacity;
//    }
//
//    public int getSimpleCapacity() {
//        return simpleCapacity;
//    }
//
//    public int getVipCapacity() {
//        return vipCapacity;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setStadiumExpenses(double stadiumExpenses) {
//        this.stadiumExpenses = stadiumExpenses;
//    }
}
