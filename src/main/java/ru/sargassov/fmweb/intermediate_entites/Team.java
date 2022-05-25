package ru.sargassov.fmweb.intermediate_entites;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sargassov.fmweb.exceptions.PlayerNotFoundException;
import ru.sargassov.fmweb.exceptions.TeamNotFoundException;

import java.math.BigDecimal;
import java.util.List;


@Data
@NoArgsConstructor
public class Team {
    private long id;
    private String name;
    private HeadCoach headCoach;
    private BigDecimal wealth;
    private League league;
    private Stadium stadium;
    private City city;
    //__________________________
    private Sponsor sponsor;
    private List<Coach> coaches;
    private List<Player> playerList;
    private List<Bank> loans;
//    private List<Market> markets;
    private BigDecimal startWealth;
    private Placement placement;
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

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                '}';
    }

    public Player findPlayerByName(String name) {
        return playerList.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElseThrow(() ->
                        new PlayerNotFoundException(String.format("Player with name = %s not found", name)));
    }

    public BigDecimal getAllPlayersCost() {
        BigDecimal allPlayersCost = BigDecimal.valueOf(0);
        for(Player p : playerList) allPlayersCost = allPlayersCost.add(p.getPrice());
        return allPlayersCost;
    }


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