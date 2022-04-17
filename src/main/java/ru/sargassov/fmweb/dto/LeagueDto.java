package ru.sargassov.fmweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.entities.League;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Component
public class LeagueDto {
    private Long id;
    private String name;
    //-------------------
    private List<SponsorDto> sponsorList;
    private List<TeamDto> teamList;
    private List<BankDto> banks;
    private List<String> youthPool;
    private TeamDto userTeam;

    @PostConstruct
    public void init(){
        sponsorList = new ArrayList<>();
        teamList = new ArrayList<>();
        banks = new ArrayList<>();
        youthPool = new ArrayList<>();
    }
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