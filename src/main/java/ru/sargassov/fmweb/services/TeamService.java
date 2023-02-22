package ru.sargassov.fmweb.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.converters.TeamConverter;
import ru.sargassov.fmweb.enums.PositionType;
import ru.sargassov.fmweb.intermediate_entities.*;
import ru.sargassov.fmweb.entity_repositories.TeamRepository;
import ru.sargassov.fmweb.intermediate_spi.*;
import ru.sargassov.fmweb.spi.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService implements TeamServiceSpi {
    private final TeamRepository teamRepository;
    private final TeamIntermediateServiceSpi teamIntermediateService;
    private final StadiumIntermediateServiceSpi stadiumIntermediateService;
    private final CityIntermediateServiceSpi cityIntermediateService;
    private final LeagueIntermediateServiceSpi leagueIntermediateService;
    private final JuniorIntermediateServiceSpi juniorIntermediateService;
    private final SponsorIntermediateServiceSpi sponsorIntermediateService;
    private final TeamConverter teamConverter;
    private final PlayerServiceSpi playerService;
    private final PlayerIntermediateServiceSpi playerIntermediateService;

    @Override
    public void loadTeams(User user){
        var league = leagueIntermediateService.getLeague(user);
        var notSavesTeams = findAll(user, league);
        var teams = teamIntermediateService.save(notSavesTeams);
        stadiumIntermediateService.assignTeamsToStadiums(teams);
        cityIntermediateService.assignTeamsToCities(teams);
        fillTeams(teams, user, league);
        fillSponsors(teams, user);
        juniorRecruitment(user);
    }

    private void fillSponsors(List<Team> teams, User user) {
        var sponsorsList = sponsorIntermediateService.findAllByUser(user);
        for (var team : teams) {
            var random = (int) (Math.random() * sponsorsList.size());
            var randomSponsor = sponsorsList.get(random);
            team.offerSponsorContract(randomSponsor);
        }
    }

    @Override
    public List<Team> findAll(User user, League league){
        log.info("TeamService.findAll");
        return teamRepository.findAll().stream()
                .map(t -> teamConverter.getIntermediateEntityFromEntity(t, user, league))
                .collect(Collectors.toList());
    }

    @Override
    public void fillTeams(List<Team> teamList, User user, League league) {
        log.info("TeamService.fillTeams");
        teamList.forEach(t -> {
            var playerList = playerService.findAllByTeamEntityId(t.getTeamEntityId(), user, league);
            t.setPlayerList(playerList);
        });
    }

    @Override
    public void juniorRecruitment(User user) {
        log.info("TeamService.juniorRecruitment");
        int maxValueOfYoungPlayersForOnePosition = 2;
        var teams = teamIntermediateService.findAllByUser(user);
        var positions = PositionType.values();
        var allJuniors = juniorIntermediateService.findByUser(user);
        for(Team currentTeam : teams){
            for(var currentPosition : positions){
                for (int i = 0; i < maxValueOfYoungPlayersForOnePosition; i++) {
                    addJuniorToTeam(currentTeam, currentPosition, user, allJuniors);
                }
            }
        }
    }

    @Override
    public void addJuniorToTeam(Team sellerTeam, PositionType position) {
        var user = UserHolder.user;
        var juniorList = juniorIntermediateService.findByUser(user);
        addJuniorToTeam(sellerTeam, position, user, juniorList);
    }

    @Override
    public void addJuniorToTeam(Team currentTeam, PositionType currentPosition, User user, List<Junior> allJuniors){
        var player = juniorIntermediateService.getYoungPlayerForPosition(currentPosition, user, allJuniors);
        var league = currentTeam.getLeague();

        player.setTeam(currentTeam);
        player.setLeague(league);
        player.setNumber(randomGuessNum(currentTeam));
        currentTeam.getPlayerList().add(player);
        playerIntermediateService.save(player);
    }

    @Transactional
    @Override
    public int randomGuessNum(Team currentTeam) {
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



//    @Transactional
//    @Override
//    public List<Team> getTeamListFromApi() {
//        return teamApi.getTeamApiList();
//    }
//
    ///////////////////////////////////////////////////////////////стартовые методы

//    @Transactional
//    @Override
//    public TeamOnPagePlayersDto getNameOfUserTeam() {
//        Team userTeam = userService.getUserTeam();
//        int index = teamApi.getTeamApiList().stream()
//                .sorted(Comparator.comparing(Team::getName))
//                .collect(Collectors.toList())
//                .indexOf(userTeam);
//
//        TeamOnPagePlayersDto userTeamDto = teamConverter.dtoToTeamOnPagePlayersDto(userTeam);
//        userTeamDto.setCountParameter(index);
//        return userTeamDto;
//    }

//    @Transactional
//    @Override
//    public List<PlayerSoftSkillDto> getAllPlayersByUserTeam(Integer parameter) {
//        log.info("TeamService.getAllPlayersByUserTeam()");
//        List<Player> players = userService.getUserTeam().getPlayerList();
//        List<PlayerSoftSkillDto> playerSoftSkillDtos = playerService.getPlayerSoftSkillDtoFromPlayer(players);
//
//        playerSoftSkillDtos.sort(teamsPlayersComparators.getComparators().get(parameter));
//        return playerSoftSkillDtos;
//    }
//
//    @Transactional
//    @Override
//    public List<PlayerSoftSkillDto> getAllPlayersByAllTeam(Integer parameter) {
//        log.info("TeamService.getAllPlayersByAllTeam()");
//        List<Team> allTeams = teamApi.getTeamApiList();
//        List<PlayerSoftSkillDto> playerSoftSkillDtos = new ArrayList<>();
//
//        for (Team t : allTeams) {
//            List<Player> teamPlayers = t.getPlayerList();
//            playerSoftSkillDtos.addAll(
//                    playerService.getPlayerSoftSkillDtoFromPlayer(teamPlayers)
//            );
//        }
//        playerSoftSkillDtos.sort(teamsPlayersComparators.getComparators().get(parameter));
//        return playerSoftSkillDtos;
//    }

//    @Transactional
//    @Override
//    public void deletePlayerFromCurrentPlacement(Integer number) {
//        Team team = userService.getUserTeam();
//        Player player = userService.getPlayerByNumberFromUserTeam(number);
//        player.setStrategyPlace(-100);
//        powerTeamCounter(team);
//    }
//    //////////////////////////////////////////////////////////////////////////////

//    @Transactional
//    @Override
//    public List<PlayerOnTrainingDto> getAllPlayersOnTrainingByUserTeam(Integer parameter) {
//        log.info("TeamService.getAllPlayersOnTrainingByUserTeam()");
//        List<Player> players = userService.getUserTeam().getPlayerList();
//        List<PlayerOnTrainingDto> playerOnTrainingDtos = playerService.getPlayerOnTrainingDtoFromPlayer(players);
//
//        playerOnTrainingDtos.sort(trainingPlayersComparators.getComparators().get(parameter));
//        return playerOnTrainingDtos;
//    }
//        //////////////////////////////////////////////////////////////////////////////
//    @Transactional
//    @Override
//    public TeamOnPagePlayersDto getNameOfOpponentTeam(Integer parameter, Integer delta) {
//        List<Team> teams = teamApi.getTeamApiList().stream()
//                .sorted(Comparator.comparing(Team::getName))
//                .collect(Collectors.toList());
//
//        parameter += delta;
//        if(parameter >= teams.size()) parameter = 0;
//        if(parameter < 0) parameter = teams.size() - 1;
//        if(teams.get(parameter) == userService.getUserTeam() && delta == 1) parameter += 1;
//        if(teams.get(parameter) == userService.getUserTeam() && delta == -1) parameter -= 1;
//
//        TeamOnPagePlayersDto teamDto = teamConverter.dtoToTeamOnPagePlayersDto(teams.get(parameter));
//        teamDto.setCountParameter(parameter);
//        return teamDto;
//    }
//    @Transactional
//    public List<PlayerSoftSkillDto> getTenPlayersFromNextTeam(String name, Integer playerParameter, Integer sortParameter) {
//        log.info("TeamService.getTenPlayersFromNextTeam(name)");
//        List<Player> players = teamApi.findByName(name).getPlayerList();
//        List<PlayerSoftSkillDto> playerSoftSkillDtos = playerService.getPlayerSoftSkillDtoFromPlayer(players);
//
//        if(playerParameter < 0) playerParameter = 0;
//        if(playerParameter > playerSoftSkillDtos.size() - 10) playerParameter = playerSoftSkillDtos.size() - 10;
//        return playerSoftSkillDtos.stream()
//                .sorted(teamsPlayersComparators.getComparators().get(sortParameter))
//                .skip(playerParameter)
//                .limit(10)
//                .collect(Collectors.toList());
//    }
//    @Transactional
//    @Override
//    public void buyNewPlayer(PlayerSoftSkillDto playerSoftSkillDto) {
//        Team opponentTeam = teamApi.findByName(playerSoftSkillDto.getClub());
//        Team userTeam = userService.getUserTeam();
//        Player player = opponentTeam.findPlayerByName(playerSoftSkillDto.getName());
//
//        if(userTeam.getWealth().compareTo(player.getPrice()) < 0)
//            throw new TooExpensiveException("Player " + player.getName() +
//                    " have price = " + player.getPrice() + " is too expensive for " + userTeam.getName());
//
//        userTeam.setWealth(userTeam.getWealth().subtract(player.getPrice()));
//        userTeam.substractTransferExpenses(player.getPrice());
//        opponentTeam.setWealth(opponentTeam.getWealth().add(player.getPrice()));
//        player.setStrategyPlace(-100);
//        player.setCapitan(false);
//        player.setTeam(userTeam);
//        userTeam.getPlayerList().add(player);
//        player.guessNumber(player.getNumber());
//
//        opponentTeam.getPlayerList().remove(player);
//        addJuniorToTeam(opponentTeam, player.getPosition());
//        captainAppointment(opponentTeam);
//
//    }
//    @Transactional
//    @Override
//    public void sellPlayer(String name) {
//        Team userTeam = userService.getUserTeam();
//        Player player = userTeam.findPlayerByName(name);
//        if(userTeam.getPlayerList().size() == 18)
//            throw new TransferException("You can't have less than 18 players in your team");
//        userTeam.getPlayerList().remove(player);
//        BigDecimal halfPriceOfPlayer = player.getPrice().divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP);
//        userTeam.setWealth(userTeam.getWealth().add(halfPriceOfPlayer));
//        userTeam.addTransferExpenses(halfPriceOfPlayer);
//    }
//


//    @Transactional
//    @Override
//    public List<InformationDto> gelAllIncomes() {
//        Team team = userService.getUserTeam();
//        return FinanceAnalytics.getIncomes(team);
//    }
//
//    @Transactional
//    @Override
//    public List<InformationDto> gelAllExpenses() {
//        Team team = userService.getUserTeam();
//        return FinanceAnalytics.getExpenses(team);
//    }

//    @Transactional
//    @Override
//    public TextResponse getStartFinanceMessage() {
//        Team userTeam = userService.getUserTeam();
//        int banksValue = userTeam.getLoans().size();
//        TextResponse text = new TextResponse();
//        text.setResponse(TextConstant.getBanksStartMessage(userTeam, banksValue));
//        return text;
//    }
//
//    @Transactional
//    @Override
//    public List<LoanDto> loadCurrentLoans() {
//        Team userTeam = userService.getUserTeam();
//        return bankService.getLoanDtoFromIntermediateEntity(userTeam);
//    }

//    @Transactional
//    @Override
//    public void remainCurrentLoan(LoanDto loan) {
//        Team team = userService.getUserTeam();
//
//        if (team.getWealth().compareTo(loan.getRemainsToPay()) < 0){
//            throw new TooExpensiveException(team.getName() + " haven't enough money (" + loan.getRemainsToPay() + ") to remains this loan!");
//        }
//
//        Bank bank = team.getBankInLoansListByTitle(loan.getTitle());
//        team.setWealth(team.getWealth().subtract(loan.getRemainsToPay()));
//        bank.remainLoan();
//        team.getLoans().remove(bank);
//        bankService.returnBankToApi(bank);
//    }

//    @Transactional
//    @Override
//    public TextResponse getStartSponsorMessage() {
//        Team userTeam = userService.getUserTeam();
//        TextResponse responce = new TextResponse();
//        String answer = TextConstant.permissionToChangeSponsor(userTeam.isChangeSponsor());
//        responce.setResponse(answer);
//        return responce;
//    }
//
//    @Transactional
//    @Override
//    public List<StartFinishInformationDto> getCurrentmarketsInfo() {
//        Team team = userService.getUserTeam();
//        List<Market> markets = team.getMarkets();
//        List<StartFinishInformationDto> dtos = new ArrayList<>();
//
//        for (Market market : markets) {
//            Day startDay = market.getStartDate();
//            Day finishDay = market.getFinishDate();
//            LocalDate startDate = startDay.getDate();
//            LocalDate finishDate = finishDay.getDate();
//
//            String startDateString = startDate.getDayOfMonth() + "." + startDate.getMonth() + "." + startDate.getYear();
//            String finishDateString = finishDate.getDayOfMonth() + "." + finishDate.getMonth() + "." + finishDate.getYear();
//            StartFinishInformationDto dto = new StartFinishInformationDto(
//                    market.getMarketTypeInString(),
//                    startDateString,
//                    finishDateString
//            );
//            dtos.add(dto);
//        }
//        return dtos;
//    }
//    @Transactional
//    @Override
//    public void addNewMarketProgram(InformationDto dto) {
//        Team team = userService.getUserTeam();
//        Market market = Market.getMarketByTitle(dto.getType());
//        Integer multyCoeff = (Integer) dto.getValue();
//        BigDecimal askingCost = market.getMarketType().getOneWeekCost().multiply(BigDecimal.valueOf(multyCoeff));
//
//        if (team.getWealth().compareTo(askingCost) < 0) {
//            throw new MarketException("Your club hasn't emough money");
//        }
//
//        if (team.getMarkets().size() >= 5) {
//            throw new MarketException("Too much marketing programs in your teams!");
//        };
//
//        Day startDay = market.getStartDate();
//        startDay.setDate(calendarService.getPresentDay().getDate());
//        Day finishDay = market.getFinishDate();
//        finishDay.setDate(startDay.getDate().plusWeeks((Integer) dto.getValue()));
//        team.getMarkets().add(market);
//        team.setWealth(team.getWealth().subtract(askingCost));
//        team.substractMarketExpenses(askingCost);
//    }
//
//    @Transactional
//    @Override
//    public List<MarketDto> loadPotencialMarketPrograms() {
//        Team userTeam = userService.getUserTeam();
//        return StadiumAnalytics.loadPotencialMarketPrograms(userTeam);
//    }

//    @Transactional
//    @Override
//    public void rejectProgram(String title) {
//        Team userTeam = userService.getUserTeam();
//        userTeam.rejectMarketProgram(title);
//    }
//
//    @Transactional
//    @Override
//    public List<InformationDto> getFullCapacityInformation() {
//        Team userTeam = userService.getUserTeam();
//        return StadiumAnalytics.getFullCapacityInformation(userTeam);
//    }
//
//    @Transactional
//    @Override
//    public void expandTheStadium(Integer delta) {
//        Team userTeam = userService.getUserTeam();
//        Stadium stadium = userTeam.getStadium();
//        if (userTeam.getWealth().compareTo(BigDecimal.ONE) < 0) {
//            log.error("Too little money to expand the stadium");
//            throw new StadiumException("Too little money to expand the stadium");
//        }
//        stadium.expand(delta);
//    }

//    @Override
//    public List<String> setTrainingEffects() {
//        List<String> noteOfChanges = new ArrayList<>();
//        List<Team> teams = teamApi.getTeamApiList();
//        Team userTeam = userService.getUserTeam();
//        for (Team t : teams) {
//            if (!t.equals(userTeam)) {
//                noteOfChanges.addAll(t.setRandomTrainingEffects());
//            }
//        }
//        return userTeam.userTeamTrainingEffects(noteOfChanges);
//    }
//
//    @Override
//    public List<String> setFinanceUpdates() {
//        Team userTeam = userService.getUserTeam();
//        Day day = dayService.getActualDay();
//        return userTeam.setFinanceUpdates(day);
//    }
//
//    @Override
//    public List<String> setMarketingChanges() {
//        Team userTeam = userService.getUserTeam();
//        return userTeam.setMarketingChanges(dayService.getActualDay());
//    }
//
//    @Override
//    public void setPlayerRecovery() {
//        List<Team> teams = teamApi.getTeamApiList();
//        for (Team t : teams) {
//            for (Player p : t.getPlayerList()) {
//                if (p.getTire() > 0) {
//                    p.setTire(p.getTire() - 5);
//                }
//            }
//        }
//    }
}


