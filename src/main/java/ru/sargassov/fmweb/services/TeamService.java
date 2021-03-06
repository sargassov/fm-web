package ru.sargassov.fmweb.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api_temporary_classes_group.TeamApi;
import ru.sargassov.fmweb.comparators.TeamsPlayersComparators;
import ru.sargassov.fmweb.comparators.TrainingPlayersComparators;
import ru.sargassov.fmweb.constants.FinanceAnalytics;
import ru.sargassov.fmweb.constants.TextConstant;
import ru.sargassov.fmweb.converters.BankConverter;
import ru.sargassov.fmweb.converters.TeamConverter;
import ru.sargassov.fmweb.dto.*;
import ru.sargassov.fmweb.dto.player_dtos.IdNamePricePlayerDto;
import ru.sargassov.fmweb.dto.player_dtos.PlayerOnTrainingDto;
import ru.sargassov.fmweb.dto.player_dtos.PlayerSoftSkillDto;
import ru.sargassov.fmweb.exceptions.PlayerNotFoundException;
import ru.sargassov.fmweb.exceptions.TooExpensiveException;
import ru.sargassov.fmweb.exceptions.TransferException;
import ru.sargassov.fmweb.intermediate_entites.*;
import ru.sargassov.fmweb.repositories.TeamRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamConverter teamConverter;
    private final PlayerService playerService;
    private final JuniorService juniorService;
    private final UserService userService;
    private final TeamApi teamApi;
    private final TeamsPlayersComparators teamsPlayersComparators;
    private final TrainingPlayersComparators trainingPlayersComparators;
    private final BankService bankService;

    public void loadTeams(){
        teamApi.setTeamApiList(findAll());
        fillTeams(teamApi.getTeamApiList());
        juniorRecruitment(teamApi.getTeamApiList());
    }

    public List<Team> findAll(){
        log.info("TeamService.getAllTeams");
        return teamRepository.findAll().stream()
                .map(teamConverter::entityToIntermediateEntity).collect(Collectors.toList());
    }

    public void fillTeams(List<Team> teamList) {
        log.info("TeamService.fillTeams");
        teamList.forEach(t -> {
            t.setPlayerList(playerService.getAllPlayersByTeamId(t.getId()));
        });
    }

    public void juniorRecruitment(List<Team> teamList) {
        log.info("TeamService.juniorRecruitment");
        int maxValueOfYoungPlayersForOnePosition = 2;

        for(Team currentTeam : teamList){
            for(Position currentPosition : Position.values()){
                for (int i = 0; i < maxValueOfYoungPlayersForOnePosition; i++) {
                    addJuniorToTeam(currentTeam, currentPosition);
                }
            }
        }
    }

    private void addJuniorToTeam(Team currentTeam, Position currentPosition){
        Player player = juniorService.getYoungPlayer(currentPosition);
        player.setTeam(currentTeam);
        player.setNumber(randomGuessNum(currentTeam));
        currentTeam.getPlayerList().add(player);
    }

    private int randomGuessNum(Team currentTeam) {
        int num = 99;
        List<Integer> ints = currentTeam.getPlayerList()
                .stream().map(Player::getNumber)
                .collect(Collectors.toList());

        while (true){
            if(ints.contains(num))
                num--;
            else
                break;
        }
        return num;
    }

    public List<Team> getTeamListFromApi() {
        return teamApi.getTeamApiList();
    }

    public Team getTeamByIdFromApi(Long id){
        return teamApi.findTeamById(id);
    }

    public Team getTeamByNameFromApi(String name){
        return teamApi.findByName(name);
    }

    public void fillPlacementForAllTeams() {
        getTeamListFromApi().stream()
                .forEach(t -> {
                    autoFillPlacement(t);
                    captainAppointment(t);
                    powerTeamCounter(t);
                });
    }

    public void autoFillPlacement(Team t) {
        log.info("TeamService.autoFillPlacement for " + t.getName());

        List<Player> playerList = t.getPlayerList().stream()
                .filter(p -> !p.isInjury())
                .collect(Collectors.toList()); // ???????????? ???????????????? ????????????

        t.getPlacement().getRoles().forEach(role -> {
            log.info(t.getName() + " " + role.getTitle());
            List<Player> suitablePlayers = getSuitablePlayers(playerList, role).stream()
                    .filter(p -> p.getStrategyPlace() == -100)
                    .collect(Collectors.toList()); // ???????????????????? ???????????? ???? ???????????????????? ??????????????

            Player selected = (findBest(suitablePlayers));
            selected.setFirstEighteen(true);
            selected.setStrategyPlace(role.getPosNumber());
        });
    }

    private Player findBest(List<Player> suitablePlayers) {
        Player best = suitablePlayers.stream().sorted((o1, o2) ->
                Integer.compare(o2.getPower(), o1.getPower()))
                .limit(1)
                .findFirst()
                .orElseThrow(() ->
                        new PlayerNotFoundException("Player in method TeamService.findBest() not found"));
        log.info(best.getName());
        return best;
    }

    private List<Player> getSuitablePlayers(List<Player> playerList, Role role) {
        return playerList.stream()
                .filter(p -> p.equalsPosition(role))
                .collect(Collectors.toList());
    }

    private void captainAppointment(Team team) {
        Player player = team.getPlayerList().stream().sorted((o1, o2) -> Integer.compare(o2.getCaptainAble(), o1.getCaptainAble()))
                .limit(1)
                .findFirst()
                .orElseThrow(() ->
                        new PlayerNotFoundException("Player in method TeamService.captainAppointment() not found"));

        player.setCapitan(true);
    }

    public void setNewCaptainHandle(String name){
        Player nowCap = null, futureCap;
        try{
            nowCap = userService.getCaptainOfUserTeam();
        }
        catch (RuntimeException r) {
            log.error("Captain function is not avalible");
        }
        futureCap = userService.getPlayerByNameFromUserTeam(name);

        nowCap.setCapitan(false);
        futureCap.setCapitan(true);
    }

    public void powerTeamCounter(Team team) {
        int power = 0;

        List<Player> playerList = team.getPlayerList().stream()
                .filter(p -> p.getStrategyPlace() > -1 && p.getStrategyPlace() < 11)
                .collect(Collectors.toList());

        for(Player p: playerList){
            power += p.getPower();
            if(p.isCapitan())
                power += p.getCaptainAble();
        }
        team.setTeamPower(power / 11);
    }
    ///////////////////////////////////////////////////////////////?????????????????? ????????????


    public TeamOnPagePlayersDto getNameOfUserTeam() {
        Team userTeam = userService.getUserTeam();
        int index = teamApi.getTeamApiList().stream()
                .sorted(Comparator.comparing(Team::getName))
                .collect(Collectors.toList())
                .indexOf(userTeam);

        TeamOnPagePlayersDto userTeamDto = teamConverter.dtoToTeamOnPagePlayersDto(userTeam);
        userTeamDto.setCountParameter(index);
        return userTeamDto;
    }

    public List<PlayerSoftSkillDto> getAllPlayersByUserTeam(Integer parameter) {
        log.info("TeamService.getAllPlayersByUserTeam()");
        List<Player> players = userService.getUserTeam().getPlayerList();
        List<PlayerSoftSkillDto> playerSoftSkillDtos = playerService.getPlayerSoftSkillDtoFromPlayer(players);

        playerSoftSkillDtos.sort(teamsPlayersComparators.getComparators().get(parameter));
        return playerSoftSkillDtos;
    }


    public void deletePlayerFromCurrentPlacement(Integer number) {
        Team team = userService.getUserTeam();
        Player player = userService.getPlayerByNumberFromUserTeam(number);
        player.setStrategyPlace(-100);
        powerTeamCounter(team);
    }
//    //////////////////////////////////////////////////////////////////////////////

    public List<PlayerOnTrainingDto> getAllPlayersOnTrainingByUserTeam(Integer parameter) {
        log.info("TeamService.getAllPlayersOnTrainingByUserTeam()");
        List<Player> players = userService.getUserTeam().getPlayerList();
        List<PlayerOnTrainingDto> playerOnTrainingDtos = playerService.getPlayerOnTrainingDtoFromPlayer(players);

        playerOnTrainingDtos.sort(trainingPlayersComparators.getComparators().get(parameter));
        return playerOnTrainingDtos;
    }
        //////////////////////////////////////////////////////////////////////////////

    public TeamOnPagePlayersDto getNameOfOpponentTeam(Integer parameter, Integer delta) {
        List<Team> teams = teamApi.getTeamApiList().stream()
                .sorted(Comparator.comparing(Team::getName))
                .collect(Collectors.toList());

        parameter += delta;
        if(parameter >= teams.size()) parameter = 0;
        if(parameter < 0) parameter = teams.size() - 1;
        if(teams.get(parameter) == userService.getUserTeam() && delta == 1) parameter += 1;
        if(teams.get(parameter) == userService.getUserTeam() && delta == -1) parameter -= 1;

        TeamOnPagePlayersDto teamDto = teamConverter.dtoToTeamOnPagePlayersDto(teams.get(parameter));
        teamDto.setCountParameter(parameter);
        return teamDto;
    }

    public List<PlayerSoftSkillDto> getTenPlayersFromNextTeam(String name, Integer playerParameter, Integer sortParameter) {
        log.info("TeamService.getTenPlayersFromNextTeam(name)");
        List<Player> players = teamApi.findByName(name).getPlayerList();
        List<PlayerSoftSkillDto> playerSoftSkillDtos = playerService.getPlayerSoftSkillDtoFromPlayer(players);

        if(playerParameter < 0) playerParameter = 0;
        if(playerParameter > playerSoftSkillDtos.size() - 10) playerParameter = playerSoftSkillDtos.size() - 10;
        return playerSoftSkillDtos.stream()
                .sorted(teamsPlayersComparators.getComparators().get(sortParameter))
                .skip(playerParameter)
                .limit(10)
                .collect(Collectors.toList());
    }

    public void buyNewPlayer(PlayerSoftSkillDto playerSoftSkillDto) {
        Team opponentTeam = teamApi.findByName(playerSoftSkillDto.getClub());
        Team userTeam = userService.getUserTeam();
        Player player = opponentTeam.findPlayerByName(playerSoftSkillDto.getName());

        if(userTeam.getWealth().compareTo(player.getPrice()) < 0)
            throw new TooExpensiveException("Player " + player.getName() +
                    " have price = " + player.getPrice() + " is too expensive for " + userTeam.getName());

        userTeam.setWealth(userTeam.getWealth().subtract(player.getPrice()));
        userTeam.substractTransferExpenses(player.getPrice());
        opponentTeam.setWealth(opponentTeam.getWealth().add(player.getPrice()));
        player.setStrategyPlace(-100);
        player.setCapitan(false);
        player.setTeam(userTeam);
        userTeam.getPlayerList().add(player);
        player.guessNumber(player.getNumber());

        opponentTeam.getPlayerList().remove(player);
        addJuniorToTeam(opponentTeam, player.getPosition());
        captainAppointment(opponentTeam);

    }

    public void sellPlayer(String name) {
        Team userTeam = userService.getUserTeam();
        Player player = userTeam.findPlayerByName(name);
        if(userTeam.getPlayerList().size() == 18)
            throw new TransferException("You can't have less than 18 players in your team");
        userTeam.getPlayerList().remove(player);
        BigDecimal halfPriceOfPlayer = player.getPrice().divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP);
        userTeam.setWealth(userTeam.getWealth().add(halfPriceOfPlayer));
        userTeam.addTransferExpenses(halfPriceOfPlayer);
    }

    public List<IdNamePricePlayerDto> getSellingList() {
        return userService.getUserTeam().getPlayerList().stream()
                .map(playerService::getIdNamePricePlayerDtoFromPlayer)
                .sorted(Comparator.comparing(IdNamePricePlayerDto::getPrice, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    public List<InformationDto> gelAllIncomes() {
        Team team = userService.getUserTeam();
        return FinanceAnalytics.getIncomes(team);
    }

    public List<InformationDto> gelAllExpenses() {
        Team team = userService.getUserTeam();
        return FinanceAnalytics.getExpenses(team);
    }

    public TextResponse getStartFinanceMessage() {
        Team userTeam = userService.getUserTeam();
        int banksValue = userTeam.getLoans().size();
        TextResponse text = new TextResponse();
        text.setResponse(TextConstant.getBanksStartMessage(userTeam, banksValue));
        return text;
    }


    public List<LoanDto> loadCurrentLoans() {
        Team userTeam = userService.getUserTeam();
        BankConverter bc = bankService.getBankConverter();
        return userTeam.getLoans().stream()
                .map(b -> bc.getLoanDtoFromIntermediateEntity(b))
                .collect(Collectors.toList());
    }

    public void remainCurrentLoan(LoanDto loan) {
        Team team = userService.getUserTeam();

        if (team.getWealth().compareTo(loan.getRemainsToPay()) < 0){
            throw new TooExpensiveException(team.getName() + " haven't enough money (" + loan.getRemainsToPay() + ") to remains this loan!");
        }

        Bank bank = team.getBankInLoansListByTitle(loan.getTitle());
        team.setWealth(team.getWealth().subtract(loan.getRemainsToPay()));
        bank.setDateOfLoan(null);
        bank.setRemainsDate(null);
        bank.setPayPerDay(null);
        bank.setPayPerWeek(null);
        bank.setPayPerMonth(null);
        bank.setFullLoanAmountValue(0.0);
        bank.setTookMoney(null);
        bank.setRemainMoney(null);
        bank.setAlreadyPaid(null);
        team.getLoans().remove(bank);
        bankService.returnBankToApi(bank);
    }

    public TextResponse getStartSponsorMessage() {
        Team userTeam = userService.getUserTeam();
        TextResponse responce = new TextResponse();
        String answer = TextConstant.permissionToChangeSponsor(userTeam.isChangeSponsor());
        responce.setResponse(answer);
        return responce;
    }


}


