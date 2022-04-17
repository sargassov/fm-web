package ru.sargassov.fmweb.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
public class PlayerDto {

    private long id;
    private String name;
    private String natio;
    private int gkAble;
    private int defAble;
    private int midAble;
    private int forwAble;
    private int captainAble;
    private int number;
    private int strategyPlace;
    private int birthYear;
    private int price;

    private PositionDto position;
    private TeamDto team;
    //------------------------
    private int tire;
    private int timeBeforeTreat;
    private int yearBirth;
    private int trainingAble;
    private int trainingBalance;
    private boolean isInjury;
    private boolean firstEleven;
    private boolean isCapitan;

    private final static Integer youngPlayerBirthYear = 2004;
//    private static PlayerPriceSetter priceSetter;
//    private static List<Position> positions;
//    private List<Integer> ables;
}

//public class Player implements Cloneable{
//
//    private String name;
//    private String natio;
//    private Team team;
//    private String teamName;
//    private Position position;
//    private League league;
//
//    private int strategyPlace;
//    private int number;
//    private int gkAble;
//    private int defAble;
//    private int midAble;
//    private int forwAble;
//    private int power;
//    private int captainAble;
//    private int tire;
//    private int timeBeforeTreat;
//    private int yearBirth;
//    private int trainingAble;
//    private int trainingBalance;
//    private int price;
//
//    private boolean isInjury;
//    private boolean firstEleven;
//    private boolean isCapitan;
//
//    private final static Integer youngPlayerBirthYear = 2004;
//    private static PlayerPriceSetter priceSetter;
//    private static List<Position> positions;
//    private List<Integer> ables;
//
//    public Player(){}
//
//    public Player(String info) {
//        init();
//
//        toComposite(info);
//        positionsAndAblesInit();
//        trainingAble = (int) (Math.random() * 10 + 10);
//        price = takePrice();
//        power = findPower();
//        strategyPlace = -100;
//
//    }
//
//    public Player(String name, int zero) { //Создание игрока
//
//        positionsAndAblesInit();
//        this.name = name;
//        natio = "Rus";
//        position = randomPosition();
//        number = zero;
//
//        youthabilities();
//        price = takePrice();
//
//    }
//
//    private void init(){
//        if(priceSetter == null){
//            priceSetter = PlayerPriceSetter.getInstance();
//        }
//        if(positions == null){
//            positions = Arrays.asList(Position.GOALKEEPER, Position.DEFENDER,
//                    Position.MIDFIELDER, Position.FORWARD);
//        }
//    }
//
//    private void toComposite(String info) { // создание юниора для пула молодых игроков
//
//        String[] mass = info.split("/");
//
//        name = mass[0];
//        yearBirth = Integer.parseInt(mass[1]);
//        natio = mass[2];
//        teamName = mass[3];
//        position = Corrector.stringInPos(mass[4]);
//        gkAble = Integer.parseInt(mass[5]);
//        defAble = Integer.parseInt(mass[6]);
//        midAble = Integer.parseInt(mass[7]);
//        forwAble = Integer.parseInt(mass[8]);
//        captainAble = Integer.parseInt(mass[10]);
//        number = Integer.parseInt(mass[11]);
//    }
//
//    private void positionsAndAblesInit(){ // создание вспомогательных массивов для реализации игрока
//        ables = Arrays.asList(gkAble, defAble, midAble, forwAble);
//    }
//
//
//    private int takePrice() { // определение цены игрока
//        return priceSetter.createPrice(this);
//    }
//
//    private int findPower(){ // определение силы игроко (влияет на цену)
//
//        for(int x = 0; x < positions.size(); x++)
//            if (this.position.equals(positions.get(x)))
//                return ables.get(x);
//
//        return 0;
//    }
//
//    private void youthabilities() { //Создание ключевых гавыков молодых игроков
//        captainAble = 1;
//
//        positions.forEach(p -> {
//            int temporary;
//
//            if(p.equals(Position.GOALKEEPER)) temporary = (int)(Math.random() * 9);
//            else temporary = (int)(Math.random() * 15);
//
//            ables.set(p.ordinal(), temporary);
//        });
//
//        positions.stream().filter(p -> p.equals(position)).forEach(pos -> {
//            int currentAble = 60;
//            currentAble += Math.random() * 5;
//            ables.set(pos.ordinal(), currentAble);
//            power = ables.get(pos.ordinal());
//        });
//
//        yearBirth = youngPlayerBirthYear;
//        trainingAble = 10 + (int)(Math.random() * 20) ;
//        setMainYoungerAbles();
//    }
//
//    private void setMainYoungerAbles() { //Перенос ключевых навыков молодых игроков из буфера
//        gkAble = ables.get(0);
//        defAble = ables.get(1);
//        midAble = ables.get(2);
//        forwAble = ables.get(3);
//    }
//
//    public void setTeam(Team team) {
//        this.team = team;
//    }
//
//    private Position randomPosition() {
//        int random = (int)(Math.random() * 4);
//        return positions.get(random);
//    }
//
//    private void numberCorrector(List<Player>playerList){
//        Random random = new Random();
//        List<Integer> numbers = playerList.stream()
//                .map(p -> p.getNumber())
//                .sorted()
//                .collect(Collectors.toList());
//
//        int x = 0;
//
//        do{
//            x = random.nextInt(99) + 1;
//        }while(numbers.contains(x));
//        number = x;
//
//
//
//
//
////        int x = 1;
////        while(x < 100){
////            boolean marker = false;
////            for(Player player : playerList){
////                if(player.number == x){
////                    marker = true;
////                    break;
////                }
////            }
////            if(!marker){
////                numbers.add(x);
////            }
////             x++;
////        }
////        int num = (int) (Math.random() * numbers.size() - 1);
////        num++;
////        return numbers.get(num);
//    }
//
//    public String strategyPlaceInPosition(){
//        if(strategyPlace > -1 && strategyPlace < 12)
//            return Corrector.wordToCenter(Corrector.posInString(position), 4);
//        else if(strategyPlace < 19)
//            return Corrector.wordToCenter("Sub", 4);
//        else
//            return Corrector.wordToCenter("", 4);
//    }
//
//    public void setNewPower() {
//        trainingBalance -= 100;
//        power++;
//
//        if(position.equals(Position.GOALKEEPER)) gkAble++;
//        if(position.equals(Position.DEFENDER)) defAble++;
//        if(position.equals(Position.MIDFIELDER)) midAble++;
//        if(position.equals(Position.FORWARD)) forwAble++;
//
//        ables = Arrays.asList(gkAble, defAble, midAble, forwAble);
//        price = takePrice();
//    }
//
//    public int getGkAble() {
//        return gkAble;
//    }
//
//    public int getDefAble() {
//        return defAble;
//    }
//
//    public int getMidAble() {
//        return midAble;
//    }
//
//    public int getForwAble() {
//        return forwAble;
//    }
//
//    public int getCaptainAble() {
//        return captainAble;
//    }
//
//    public int getYearBirth() {
//        return yearBirth;
//    }
//
//    public int getPrice() {
//        return price;
//    }
//
//    public Team getTeam() {
//        return team;
//    }
//
//    public String getTeamName() {
//        return teamName;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public Position getPosition() {
//        return position;
//    }
//
//    public void setPosition(Position position) {
//        this.position = position;
//    }
//
//    public void setStrategyPlace(Integer strategyPlace) {
//        this.strategyPlace = strategyPlace;
//    }
//
//    public int getStrategyPlace() {
//        return strategyPlace;
//    }
//
//    public int getPower() {
//        return power;
//    }
//
//    public boolean isPlayerInjury() {
//        return isInjury;
//    }
//
//    public boolean isFirstEleven() {
//        return firstEleven;
//    }
//
//    public void setFirstEleven(boolean firstEleven) {
//        this.firstEleven = firstEleven;
//    }
//
//    public void setCapitan(boolean capitan) {
//        isCapitan = capitan;
//    }
//
//    public boolean isCapitan() {
//        return isCapitan;
//    }
//
//    public static List<Position> getPositions() {
//        return positions;
//    }
//
//    public void setNumber(int number) {
//        this.number = number;
//    }
//
//    public void getNumberCorrector(List<Player> playerList){
//        numberCorrector(playerList);
//    }
//
//    public int getNumber() {
//        return number;
//    }
//
//    public League getLeague() {
//        return league;
//    }
//
//    public void setLeague(League league) {
//        this.league = league;
//    }
//
//    public int getTrainingAble() {
//        return trainingAble;
//    }
//
//    public String getNatio() {
//        return natio;
//    }
//
//    public int getTimeBeforeTreat() {
//        return timeBeforeTreat;
//    }
//
//    @Override
//    public Player clone() throws CloneNotSupportedException {
//        return (Player)super.clone();
//    }
//
//    public int getTire() {
//        return tire;
//    }
//}






