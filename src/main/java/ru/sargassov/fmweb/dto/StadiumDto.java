package ru.sargassov.fmweb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
public class StadiumDto {
    private long id;
    private TeamDto team;
    private String title;
    private LeagueDto league;
    private int fullCapacity;
    private CityDto city;
    //___________________________

    //    List<Integer> tempCapacityArr;
//    List<Integer> tempCostArr;
//
//    private League league;
//    private String title;
//
//    private int fullCapacity;
//    private int vipCapacity;
//    private int vipTicketCost;
//    private int familyCapacity;
//    private int familyTicketCost;
//    private int fanCapacity;
//    private int fanTicketCost;
//    private int simpleCapacity;
//    private int simpleTicketCost;
//    private int awayCapacity;
//    private int awayTicketCost;
//    private int usualAverageCapacity;
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
