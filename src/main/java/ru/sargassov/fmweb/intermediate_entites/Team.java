package ru.sargassov.fmweb.intermediate_entites;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.sargassov.fmweb.dto.FinalPayment;
import ru.sargassov.fmweb.exceptions.BankNotFoundException;
import ru.sargassov.fmweb.exceptions.MarketException;
import ru.sargassov.fmweb.exceptions.PlayerNotFoundException;
import ru.sargassov.fmweb.intermediate_entites.days.Day;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


@Data
@NoArgsConstructor
@Slf4j
public class Team {
    private long id;
    private String name;
    private HeadCoach headCoach;
    private BigDecimal wealth;
    private League league;
    private Stadium stadium;
    private City city;
    //__________________________

    public static final int maxValueOfLoans = 5;
    public static final int maxValueOfMarkets = 5;
    public static final int maxValueOfCoaches = 6;
    //////////////////////////

    private Sponsor sponsor;
    private List<Coach> coaches;
    private List<Player> playerList;
    private List<Bank> loans;
    private List<Market> markets;

    private BigDecimal startWealth;
    private BigDecimal transferExpenses;
    private BigDecimal personalExpenses;
    private BigDecimal marketExpenses;
    private BigDecimal stadiumExpenses;
    private Placement placement;
    private int regularCapacity;
    private int temporaryTicketCost;
    private int teamPower;
    private boolean changeSponsor;
    private int games;
    private int won;
    private int drawn;
    private int lost;
    private int scored;
    private int missed;
    private int points;


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

    public Bank getBankInLoansListByTitle(String title){
        return loans.stream()
                .filter(b -> b.getTitle().equals(title))
                .findFirst()
                .orElseThrow(()
                        -> new BankNotFoundException(String.format("Bank with title = %s not found", title)));
    }

    public void rejectMarketProgram(String title) {
        Market currentMarket = markets.stream()
                .filter(m -> m.getMarketType().toString().equals(title))
                .findFirst()
                .orElseThrow(()
                        -> new MarketException(String.format("Market program with title = %s not found", title)));

        markets.remove(currentMarket);
    }

    public int calculateTeamPoints() {
        return won * 3 + drawn;
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
        List<String> noteOfChanges = new ArrayList<>();
        noteOfChanges.add("Training effcts of " + name);
        int size = playerList.size();

        for(int x = 0; x < 5; x++){
            Player p = playerList.get((int) (Math.random() * size));
            int balance = p.getTrainingBalance();
            int trainingAble = p.getTrainingAble();
            int trainingEffect = trainingAble * (int) (Math.random() * 5);

            p.setTire(p.getTire() + 15);
            p.setTrainingBalance(balance + trainingEffect);
            noteOfChanges.add(p.getName() + " from " + name + " increase his training balance +" + p.getTrainingAble());
            p.levelUpCheckAuto();
        }
        return noteOfChanges;
    }

    public List<String> userTeamTrainingEffects(List<String> noteOfChanges) {
        noteOfChanges.add("Your team training effects:");

        for (Coach coach : coaches) {
            if (coach.getPlayerOnTraining() != null) {
                Player player = coach.getPlayerOnTraining();
                Coach.CoachProgram program = coach.getCoachProgram();
                int playerTrainingBalance = player.getTrainingBalance();
                int playerTriningAble = player.getTrainingAble();
                int coachTrainingAble = coach.getTrainingAble();
                player.setTrainingBalance(playerTrainingBalance + coachTrainingAble);
                noteOfChanges.add(player.getName() + " in your club increase his training balance +" + (playerTriningAble * coach.getTrainingCoeff()));
                player.levelUpCheckManual(coach);
                player.guessTrainingTire(program);

                if (player.getTire() > 50) {
                    coach.setPlayerOnTraining(null);
                }
            }
        }
        return noteOfChanges;
    }

    public List<String> setFinanceUpdates(Day day) {
        List<String> notesOfChanges = new ArrayList<>();
        wealth = wealth.add(sponsor.getDayWage());
        notesOfChanges.add("Sponsor " + sponsor.getName() + " gave your team " + sponsor.getDayWage() + " Euro. It is day wage.");

        if(day.isMatch()){
            wealth = wealth.add(sponsor.getMatchWage());
            notesOfChanges.add("Sponsor " + sponsor.getName() + " gave your team " + sponsor.getMatchWage() + " Euro. It is match wage.");
        }

        return expensesUpdate(notesOfChanges, day);
    }

    private List<String> expensesUpdate(List<String> notesOfChanges, Day day) {
        for (int x = 0; x < loans.size(); x++) {
            DayOfWeek dayOfWeek = day.getDate().getDayOfWeek();
            DayOfWeek loanDayOfWeek = loans.get(x).getDateOfLoan().getDate().getDayOfWeek();
            int dayOfMonth = day.getDate().getDayOfMonth();
            int loanDayOfMonth = loans.get(x).getDateOfLoan().getDate().getDayOfMonth();
            FinalPayment finalPayment = new FinalPayment(false);

            if (loans.get(x).getTypeOfReturn().equals(Bank.TypeOfReturn.PER_DAY)) {
                notesOfChanges.addAll(
                        loans.get(x).paymentPeriod(Bank.TypeOfReturn.PER_DAY, this, finalPayment)
                );
            }

            else if (loans.get(x).getTypeOfReturn().equals(Bank.TypeOfReturn.PER_WEEK)
                    && dayOfWeek.equals(loanDayOfWeek)) {

                notesOfChanges.addAll(
                        loans.get(x).paymentPeriod(Bank.TypeOfReturn.PER_WEEK, this, finalPayment)
                );
            }

            else if(loans.get(x).getTypeOfReturn().equals(Bank.TypeOfReturn.PER_MONTH)
                    && dayOfMonth == loanDayOfMonth) {

                notesOfChanges.addAll(
                        loans.get(x).paymentPeriod(Bank.TypeOfReturn.PER_MONTH, this, finalPayment)
                );
            }

            if (finalPayment.isFinal()) {
                x--;
            }
        }
        return notesOfChanges;
    }

    public List<String> setMarketingChanges(Day day) {
        List<String> notesOfChanges = new ArrayList<>();
        for (int x = 0; x < markets.size(); x++) {
            int capacity = stadium.getUsualAverageCapacity();
            Market.MarketType marketType = markets.get(x).getMarketType();
            int newFansValue = (int) (capacity + capacity / 100 * marketType.capacityCoeff);

            stadium.setUsualAverageCapacity(newFansValue);
            stadium.setSimpleCapacity(newFansValue);
            notesOfChanges.add("New Stadium capacity is " + stadium.getUsualAverageCapacity());
            if(markets.get(x).getFinishDate().equals(day.getDate())){
                markets.remove(markets.get(x));
                log.info("Market program is finished");
                x--;
            }
        }
        return notesOfChanges;
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