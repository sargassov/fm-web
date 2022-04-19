package ru.sargassov.fmweb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.Manager;
import ru.sargassov.fmweb.entities.Sponsor;
import ru.sargassov.fmweb.entities.Stadium;

import java.util.List;


@Data
@NoArgsConstructor
public class TeamDto {
    private long id;
    private String name;
    private HeadCoach headCoach;
    private double wealth;
    private LeagueDto league;
    private StadiumDto stadium;
    private CityDto city;
    //__________________________
    private SponsorDto sponsorDto;
    private List<Coach> coaches;
    private List<PlayerDto> playerList;
    private List<BankDto> loans;
//    private List<Market> markets;
    private double startWealth;
    private PlacementDto placementDto;
    private int regularCapacity;
    private int temporaryTicketCost;
    private int teamPower;
    private long transferExpenses;
    private long personalExpenses;
    private long marketExpenses;
    private long stadiumExpenses;
    private boolean changeSponsor;
    public final int maxValueOfLoans = 5;
    public final int maxValueOfCoaches = 6;

//    public Team(String info) {
//        //        markets = new ArrayList<>();
//        coaches = new ArrayList<>();
//        playerList = new ArrayList<>();
//        loans = new ArrayList<>();
//
//        String[] teamInfo = info.split("/");
//
//        name = teamInfo[0];
//        homeTown = teamInfo[1];
//        stadium = new Stadium(teamInfo[2], Integer.parseInt(teamInfo[4]));
//        if(coaches.size() == 0) {coaches.add(new Manager(teamInfo[3]));}
//
//        wealth = Long.parseLong(teamInfo[5]) * 1_000_000;
//        startWealth = wealth;
//        temporaryTicketCost = 60;
//        maxValueOfLoans = 5;
//
//    }
//
//    private void addToSponsor() {
//        Random random = new Random();
//        sponsor = league.getSponsorList().get(random.nextInt(16));
//        wealth += sponsor.getContractBonusWage();
////        regularCapacity = capacityStad / 4;
//        System.out.println(name + " " + sponsor.getName());
//    }
//
//
//    public List<Player> getPlayerList() {
//        return playerList;
//    }
//
//    public void addToPlayerList(Player player){
//        player.setTeam(this);
//        player.setLeague(league);
//        player.getNumberCorrector(playerList);
//        playerList.add(player);
//    }
//
////    public static void setLeague(League league) {
////        Team.league = league;
////    }
//
//    public String getName() {
//        return name;
//    }
//
//    //
//
////
////    public void breakSponsorContract(){
////        wealth -= sponsor.getContractBonusWage();
////        System.out.println("\n" + Corrector.getS(35) + "Contract with " + sponsor.getName() + " was broken!\n" +
////                Corrector.getS(35) + sponsor.getContractBonusWage() + " gived away from team budget!");
////        sponsor = null;
////
////    }
////
////    public void offerSponsorContract(Sponsor sponsor) {
////        this.sponsor = sponsor;
////        wealth += sponsor.getContractBonusWage();
////        System.out.println("\n" + Corrector.getS(30) + "Contract with " + sponsor.getName() + " was offered!\n" +
////                Corrector.getS(30) + sponsor.getContractBonusWage() + " came into team budget!");
////    }
////
////    public int teamCounter(){
////        for (int i = 0; i < rfpl.teams.length; i++) {
////            if(name.equals(rfpl.teams[i].name))
////                return i;
////        }
////        return -1;
////    }
////
////    public String nameOfTeamInRegister() {
////
////        char[] nameOfTeam = name.toCharArray();
////        StringBuilder toReturn = new StringBuilder();
////        for(int x = 0; x < nameOfTeam.length; x++){
////            toReturn.append(nameOfTeam[x]);
////            toReturn.append(' ');
////        }
////
////        return new String(toReturn);
////    }
////
////    public int getPoints(){
////        return wins * 3 + draws;
////    }
////
//
//    public long getWealth() {
//        return wealth;
//    }
//
//    public void setWealth(long wealth) {
//        this.wealth = wealth;
//    }
//
//    public List<Bank> getLoans() {
//        return loans;
//    }
//
//
//    public void selectSponsor() {
//        addToSponsor();
//    }
//
//    public void setLeague(League league) {
//        this.league = league;
//    }
//
//    public void setPlacement(Placement placement) {
//        this.placement = placement;
//    }
//
//    public Placement getPlacement() {
//        return placement;
//    }
//
//    public int getTeamPower() {
//        return teamPower;
//    }
//
//    public void setTeamPower(int teamPower) {
//        this.teamPower = teamPower;
//    }
//
//    public List<Manager> getCoaches() {
//        return coaches;
//    }
//
//    public void setCoaches(List<Manager> coaches) {
//        this.coaches = coaches;
//    }
//
//    public League getLeague() {
//        return league;
//    }
//
//    public Stadium getStadium() {
//        return stadium;
//    }
}