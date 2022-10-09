package ru.sargassov.fmweb.intermediate_entities;

import lombok.*;
import ru.sargassov.fmweb.constants.BaseUserEntity;

import javax.persistence.*;

@Entity
@Table(name = "league")
@Getter
@Setter
@RequiredArgsConstructor
public class League extends BaseUserEntity {

    @Column(name = "name")
    private String name;

//    private List<List<Day>> calendar;
//    private List<Tour> allTourDates;

//
//    public League(String leagueName) {
//        this.leagueName = leagueName;
//        sponsorList = new ArrayList<>();
//        teamList = new ArrayList<>();
//        youthPool = new ArrayList<>();
//        banks = new ArrayList<>();
//    }
//
//    public String getLeagueName() {
//        return leagueName;
//    }
//
//    public List<Sponsor> getSponsorList() {
//        return sponsorList;
//    }
//
//    public List<Team> getTeamList() {
//        return teamList;
//    }
//
//    public void addToTeamList(Team t) {
//        t.setLeague(this);
//        teamList.add(t);
//    }
//
//    public void addToBankList(Bank b) {
//        b.setLeague(this);
//        banks.add(b);
//    }
//
//    public void setTeamList(List<Team> teamList) {
//        this.teamList = teamList;
//    }
//
//    public List<Player> getYouthPool() {
//        return youthPool;
//    }
//
//    public List<Bank> getBanks() {
//        return banks;
//    }
//
//    public void setBanks(List<Bank> banks) {
//        this.banks = banks;
//    }
//
//    public List<List<Day>> getCalendar() {
//        return calendar;
//    }
//
//    public void setCalendar(List<List<Day>> calendar) {
//        this.calendar = calendar;
//    }
//
//    public List<Tour> getAllTourDates() {
//        return allTourDates;
//    }
//
//    public void setAllTourDates(List<Tour> allTourDates) {
//        this.allTourDates = allTourDates;
//    }
}