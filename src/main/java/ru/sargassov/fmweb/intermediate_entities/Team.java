package ru.sargassov.fmweb.intermediate_entities;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import ru.sargassov.fmweb.constants.BaseUserEntity;
import ru.sargassov.fmweb.enums.PositionType;
import ru.sargassov.fmweb.exceptions.BankNotFoundException;
import ru.sargassov.fmweb.exceptions.MarketException;
import ru.sargassov.fmweb.exceptions.PlayerNotFoundException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.sargassov.fmweb.constants.Constant.DEFAULT_STRATEGY_PLACE;
import static ru.sargassov.fmweb.enums.PositionType.DEFENDER;
import static ru.sargassov.fmweb.enums.PositionType.FORWARD;
import static ru.sargassov.fmweb.enums.PositionType.GOALKEEPER;
import static ru.sargassov.fmweb.enums.PositionType.MIDFIELDER;


@Entity
@Slf4j
@Table(name = "team")
@Getter
@Setter
@RequiredArgsConstructor
public class Team extends BaseUserEntity {

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "id_head_coach")
    private HeadCoach headCoach;

    @Column(name = "wealth")
    private BigDecimal wealth;

    @ManyToOne
    @JoinColumn(name = "id_league")
    private League league;

    @OneToOne
    @JoinColumn(name = "id_stadium")
    private Stadium stadium;

    @ManyToOne
    @JoinColumn(name = "id_city")
    private City city;

    @OneToOne
    @JoinColumn(name = "id_sponsor")
    private Sponsor sponsor;

    @OneToMany(mappedBy="team")
    private List<Coach> coaches;

    @OneToMany(mappedBy="team")
    private List<Player> playerList;

    @OneToMany(mappedBy="team")
    private List<Bank> loans;

    @OneToMany(mappedBy="team")
    private List<Market> markets;

    @Column(name = "start_wealth")
    private BigDecimal startWealth;

    @Column(name = "transfer_expenses")
    private BigDecimal transferExpenses;

    @Column(name = "personal_expenses")
    private BigDecimal personalExpenses;

    @Column(name = "market_expenses")
    private BigDecimal marketExpenses;

    @Column(name = "stadium_expenses")
    private BigDecimal stadiumExpenses;

    @ManyToOne
    @JoinColumn(name = "id_placement")
    private Placement placement;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "regular_capacity")
    private int regularCapacity;

    @Column(name = "temporary_ticket_cost")
    private int temporaryTicketCost;

    @Column(name = "team_power")
    private int teamPower;

    @Column(name = "change_sponsor")
    private boolean changeSponsor;

    @Column(name = "games")
    private int games;

    @Column(name = "won")
    private int won;

    @Column(name = "drawn")
    private int drawn;

    @Column(name = "lost")
    private int lost;

    @Column(name = "scored")
    private int scored;

    @Column(name = "missed")
    private int missed;

    @Column(name = "points")
    private int points;

    @Column(name = "id_team_entity")
    private Long teamEntityId;

    //__________________________

    public static final int MAX_VALUE_OF_LOANS = 5;
    public static final int MAX_VALUE_OF_MARKETS = 5;
    public static final int MAX_VALUE_OF_COACHES = 6;
    //////////////////////////


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

    public BigDecimal getDailyExpenses(){
        Function<Bank, BigDecimal> payPerDayFunction = Bank::getPayPerDay;
        return getExpenses(payPerDayFunction);
    }

    public BigDecimal getWeeklyExpenses(){
        Function<Bank, BigDecimal> payPerWeekFunction = Bank::getPayPerWeek;
        return getExpenses(payPerWeekFunction);
    }

    public BigDecimal getMonthlyExpenses(){
        Function<Bank, BigDecimal> payPerMonthFunction = Bank::getPayPerMonth;
        return getExpenses(payPerMonthFunction);
    }

    private BigDecimal getExpenses(Function<Bank, BigDecimal> function) {
        BigDecimal expenses = BigDecimal.valueOf(0);
        for(Bank b : loans)
            expenses = expenses.add(function.apply(b));
        return expenses;
    }

    public List<Coach> getCoaches() {
        if (CollectionUtils.isEmpty(coaches)) {
            coaches = new ArrayList<>();
        }
        return coaches;
    }

    public void addPersonalExpenses(BigDecimal value){
        personalExpenses = personalExpenses.add(value);
    }

    public void substractPersonalExpenses(BigDecimal value){
        personalExpenses = personalExpenses.subtract(value);
    }

    public void substractTransferExpenses(BigDecimal value){
        transferExpenses = transferExpenses.subtract(value);
    }

    public void addTransferExpenses(BigDecimal value){
        transferExpenses = transferExpenses.add(value);
    }

    public void addMarketExpenses(BigDecimal value){
        marketExpenses = marketExpenses.add(value);
    }

    public void substractMarketExpenses(BigDecimal value){
        marketExpenses = marketExpenses.subtract(value);
    }

    public void addStadiumExpenses(BigDecimal value){
        stadiumExpenses = stadiumExpenses.add(value);
    }

    public void substractStadiumExpenses(BigDecimal value){
        stadiumExpenses = stadiumExpenses.subtract(value);
    }

    public void deleteBankById(Long id){
        var bank = loans.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow(()
                        -> new BankNotFoundException(String.format("Bank with id = %d not found", id)));
        loans.remove(bank);
    }

    public void rejectMarketProgram(String title) {
        Market currentMarket = markets.stream()
                .filter(m -> m.getMarketType().toString().equals(title))
                .findFirst()
                .orElseThrow(()
                        -> new MarketException(String.format("Market program with title = %s not found", title)));

        markets.remove(currentMarket);
    }

    public void calculateTeamPoints() {
        points = won * 3 + drawn;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Team)) {
            return false;
        }

        if (o == this) {
            return true;
        }

        if (name.equals(((Team) o).getName())) {
            return true;
        }

        return false;
    }

    public boolean isPlayerExists(String name) {
        Optional<Player> opt = playerList.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst();

        return opt.isPresent();
    }

    public List<String> setRandomTrainingEffects() {
        var noteOfChanges = new ArrayList<String>();
        noteOfChanges.add("Training effcts of " + name);
        var size = playerList.size();

        for(var x = 0; x < 5; x++){
            var p = playerList.get((int) (Math.random() * size));
            var balance = p.getTrainingBalance();
            var trainingAble = p.getTrainingAble();
            var trainingEffect = trainingAble * (int) (Math.random() * 5);

            p.setTire(p.getTire() + 15);
            p.setTrainingBalance(balance + trainingEffect);
            noteOfChanges.add(p.getName() + " from " + name + " increase his training balance +" + p.getTrainingAble());
            p.levelUpCheckAuto();
        }
        return noteOfChanges;
    }

    public void captainAppointment() {
        var player = playerList.stream()
                .sorted((o1, o2) -> Integer.compare(o2.getCaptainAble(), o1.getCaptainAble()))
                .limit(1)
                .findFirst()
                .orElseThrow(() ->
                        new PlayerNotFoundException("Player in method TeamService.captainAppointment() not found"));

        player.setCapitan(true);
    }

    public void powerTeamCounter() {
        int power = 0;

        var countList = playerList.stream()
                .filter(p -> p.getStrategyPlace() > -1 && p.getStrategyPlace() < 11)
                .collect(Collectors.toList());

        for(Player p: countList){
            power += p.getPower();
            if(p.isCapitan())
                power += p.getCaptainAble();
        }
        teamPower = power / 11;
    }

    public void assignUser(User user) {
        headCoach.setUser(user);
    }

    public void offerSponsorContract(Sponsor sponsor) {
        this.sponsor = sponsor;
        var contractBonusWage = sponsor.getContractBonusWage();
        wealth = wealth.add(contractBonusWage);
    }

    public void resetTeamPower() {
        teamPower = 0;
    }

    public void resetAllStrategyPlaces() {
        for(var p : playerList) {
            p.setStrategyPlace(DEFAULT_STRATEGY_PLACE);
            p.setFirstEighteen(false);
        }
    }

    public List<Player> findPlayersByPosition(PositionType position) {
        return playerList.stream()
                .filter(p -> p.getPosition().equals(position))
                .collect(Collectors.toList());
    }

    public Player findPlayerByNumber(Integer anotherPlayerPositionNumber) {
        var optPlayer = playerList.stream()
                .filter(p -> p.getNumber().equals(anotherPlayerPositionNumber))
                .findFirst();

        if (optPlayer.isPresent()) {
            return optPlayer.get();
        }
        throw new PlayerNotFoundException("Players with number #" + anotherPlayerPositionNumber + " not found");
    }

    public Player getCaptainOfTeam() {
        return playerList.stream()
                .filter(Player::isCapitan)
                .findFirst()
                .orElseThrow();
    }

    public Coach getCoachById(Long coachId) {
        return coaches.stream()
                .filter(c -> c.getId().equals(coachId))
                .findFirst()
                .orElseThrow();
    }

    public List<Player> getNextTenPlayersForTransferList(Integer playerParameter, Comparator<Player> comparator) {
        var playerListSize = playerList.size();
        var maxTransferWindowSize = 10;
        if (playerParameter < 0) {
            playerParameter = 0;
        }
        if (playerParameter > playerListSize - maxTransferWindowSize) {
            playerParameter = playerListSize - maxTransferWindowSize;
        }
        return playerList.stream()
                .sorted(comparator)
                .skip(playerParameter)
                .limit(10)
                .collect(Collectors.toList());
    }

    public Player findPlayerById(Long id) {
        return playerList.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Player> getMatchApplication() {
        return playerList.stream()
                .filter(p -> p.getStrategyPlace() >= 0 && p.getStrategyPlace() < 11)
                .sorted(Comparator.comparing(Player::getStrategyPlace, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    public Long getInjuriesValue() {
        return playerList.stream()
                .filter(Player::isInjury)
                .count();
    }

    public Player getBestSubstitution(PositionType substitutionPosition) {
        return playerList.stream()
                .filter(p -> p.getStrategyPlace() > 10)
                .filter(p -> p.getPosition().equals(substitutionPosition))
                .max(Comparator.comparing(Player::getPower))
                .orElse(null);
    }

    public Player getAnyValidSubstitution(PositionType substitutionPosition) {
        String compareField;
        var playerAbleMap = new HashMap<Integer, Player>();
        if (substitutionPosition.equals(GOALKEEPER)) {
            compareField = "gkAble";
        } else if (substitutionPosition.equals(DEFENDER)) {
            compareField = "defAble";
        } else if (substitutionPosition.equals(MIDFIELDER)) {
            compareField = "midAble";
        } else if (substitutionPosition.equals(FORWARD)) {
            compareField = "forwAble";
        } else {
            throw new IllegalStateException("Illegal position");
        }

        for (var player : playerList) {
            if (player.getStrategyPlace() > 10) {
                try {
                    var field = player.getClass().getField(compareField);
                    field.setAccessible(true);
                    playerAbleMap.put((Integer) field.get(player), player);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        var result =  playerAbleMap.entrySet().stream()
                .max(Map.Entry.comparingByKey())
                .orElse(null);
        return result.getValue();
    }

    public Player findPlayerByStrategyPlace(int strategyPlace) {
        if (strategyPlace < 0 || strategyPlace > 17) {
            throw new IllegalStateException("illegal strategtPlace");
        }
        return playerList.stream()
                .filter(p -> p.getStrategyPlace() == strategyPlace)
                .findFirst()
                .orElseThrow();
    }

    public Bank getLoanById(Long id) {
        var bank = loans.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst();
        if (bank.isEmpty()) {
            throw new BankNotFoundException("Bank with number #" + id + " not found");
        }
        return bank.get();
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
//        regularCapacity = capacityStad / 4;
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