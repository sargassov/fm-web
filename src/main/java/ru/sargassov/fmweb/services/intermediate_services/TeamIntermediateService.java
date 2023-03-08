package ru.sargassov.fmweb.services.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.comparators.TeamsPlayersComparators;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.converters.PlayerConverter;
import ru.sargassov.fmweb.converters.TeamConverter;
import ru.sargassov.fmweb.dto.FinalPayment;
import ru.sargassov.fmweb.dto.player_dtos.PlayerSoftSkillDto;
import ru.sargassov.fmweb.dto.team_dtos.TeamOnPagePlayersDto;
import ru.sargassov.fmweb.exceptions.PlayerNotFoundException;
import ru.sargassov.fmweb.intermediate_entities.Bank;
import ru.sargassov.fmweb.intermediate_entities.Day;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Role;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.repositories.intermediate_repositories.TeamIntermediateRepository;
import ru.sargassov.fmweb.spi.intermediate_spi.BankIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.CoachIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.DayIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.MarketIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.PlayerIntermediateServiceSpi2;
import ru.sargassov.fmweb.spi.intermediate_spi.RoleIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.TeamIntermediateServiceSpi;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.sargassov.fmweb.constants.Constant.DEFAULT_STRATEGY_PLACE;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class TeamIntermediateService implements TeamIntermediateServiceSpi {

    private final TeamIntermediateRepository repository;
    private final TeamConverter teamConverter;
    private final RoleIntermediateServiceSpi roleIntermediateService;
    private final TeamsPlayersComparators teamsPlayersComparators;
    private final DayIntermediateServiceSpi dayIntermediateService;
    private final BankIntermediateServiceSpi bankIntermediateService;
    private final CoachIntermediateServiceSpi coachIntermediateService;
    private final MarketIntermediateServiceSpi marketIntermediateService;
    private final PlayerIntermediateServiceSpi2 playerIntermediateService;

    @Override
    public List<Team> save(List<Team> newTeamsWithoutId) {
        return repository.saveAll(newTeamsWithoutId);
    }

    @Override
    public List<Team> findAllByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    public Team save(Team team) {
        return repository.save(team);
    }

    @Override
    public void fillPlacementForAllTeams(User user) {
        findAllByUser(user).
                forEach(this::fillPlacementForCurrentTeam);
    }

    @Override
    public void fillPlacementForCurrentTeam (Team team) {
        autoFillPlacement(team, false);
        captainAppointment(team);
        powerTeamCounter(team);
    }

    @Override
    public void powerTeamCounter(Team team) {
        team.powerTeamCounter();
    }

    @Override
    public Team findByNameAndUser(String name, User user) {
        return repository.findByNameAndUser(name, user);
    }

    @Override
    public List<Team> findAllByUserSortedByName(User user) {
        return findAllByUser(user).stream()
                .sorted(Comparator.comparing(Team::getName))
                .collect(Collectors.toList());
    }

    @Override
    public Team findByTeamEntityIdAndUser(Long teamEntityId, User user) {
        return repository.findByTeamEntityIdAndUser(teamEntityId, user);
    }

    @Override
    public TeamOnPagePlayersDto getNameOfUserTeam() {
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = repository.getById(userTeamId);
        return teamConverter.dtoToTeamOnPagePlayersDto(userTeam);
    }

    @Override
    public void captainAppointment(Team team) {
        team.captainAppointment();
    }

    @Override
    @Transactional
    public void autoFillPlacement(Team t, boolean matchPrepare) {
        log.info("TeamService.autoFillPlacement for " + t.getName());

        var healhtyPlayersSelect = healhtyPlayersSelect(t); // только здоровые игроки
        var roles = matchPrepare
                ? t.getPlacement().getRoles()
                : roleIntermediateService.findByPlacement(t.getPlacement());
        roles.forEach(role -> {
            log.info(t.getName() + " " + role.getTitle());
            var suitablePlayers = getSuitablePlayers(healhtyPlayersSelect, role).stream()
                    .filter(p -> p.getStrategyPlace() == DEFAULT_STRATEGY_PLACE)
                    .collect(Collectors.toList());// подходящие игроки на конкретную позицию

            var selectedPlayer = (findBest(suitablePlayers));
            selectedPlayer.setFirstEighteen(true);
            selectedPlayer.setStrategyPlace(role.getPosNumber());
            role.setPlayer(selectedPlayer);
            if (role.getId() == null) {
                roleIntermediateService.save(role);
            }
        });
    }

    @Override
    public Player findBest(List<Player> suitablePlayers) {
        Player best = suitablePlayers.stream().sorted((o1, o2) ->
                        Integer.compare(o2.getPower(), o1.getPower()))
                .limit(1)
                .findFirst()
                .orElseThrow(() ->
                        new PlayerNotFoundException("Player in method TeamService.findBest() not found"));
        log.info(best.getName());
        return best;
    }

    private List<Player> healhtyPlayersSelect(Team team) {
        return team.getPlayerList()
                .stream()
                .filter(p -> !p.isInjury())
                .collect(Collectors.toList());
    }

    @Override
    public List<Player> getSuitablePlayers(List<Player> playerList, Role role) {
        return playerList.stream()
                .filter(p -> p.equalsPosition(role))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void setNewCaptainHandle(String name){
        Player nowCap = null, futureCap;
        var userTeam = UserHolder.user.getUserTeam();
        try{
            nowCap = playerIntermediateService.findCaptainByTeam(userTeam);
            nowCap.setCapitan(false);
        }
        catch (RuntimeException r) {
            log.error("Captain function is not avalible");
        }
        futureCap = getPlayerByNameFromUserTeam(name);
        futureCap.setCapitan(true);
        playerIntermediateService.save(futureCap);
        playerIntermediateService.save(nowCap);
    }

    @Override
    public Team getById(Long teamId) {
        var optTeam = repository.findById(teamId);
        if (optTeam.isPresent()) {
            return optTeam.get();
        }
        throw new EntityNotFoundException("Team with id #" + teamId + " not found");
    }

    private Player getPlayerByNameFromUserTeam(String name) {
        var user = UserHolder.user;
        var userTeamId = user.getUserTeam().getId();
        var userTeam = getById(userTeamId);
        return userTeam.findPlayerByName(name);
    }

    private Player getCaptainOfUserTeam() {
        var user = UserHolder.user;
        var userTeam = user.getUserTeam();
        return userTeam.getCaptainOfTeam();
    }

    @Override
    @Transactional
    public List<PlayerSoftSkillDto> loadPlayersSort(Integer parameter) {
        log.info("TeamService.getAllPlayersByAllTeam()");
        var teams = repository.findByUser(UserHolder.user);
        var playerSoftSkillDtos = new ArrayList<PlayerSoftSkillDto>();

        for (var t : teams) {
            var teamPlayers = t.getPlayerList();
            playerSoftSkillDtos.addAll(PlayerConverter.getPlayerSoftSkillDtoFromPlayer(teamPlayers));
        }
        playerSoftSkillDtos.sort(teamsPlayersComparators.getPlayerSoftSkillDtoComparators().get(parameter));
        return playerSoftSkillDtos;
    }

    @Override
    @Transactional
    public List<String> setTrainingEffects() {
        var noteOfChanges = new ArrayList<String>();
        var user = UserHolder.user;
        var teams = repository.findByUser(user);
        var userTeamId = user.getUserTeam().getId();
        var userTeam = getById(userTeamId);
        for (var t : teams) {
            if (!t.equals(userTeam)) {
                noteOfChanges.addAll(t.setRandomTrainingEffects());
            }
        }
        return userTeamTrainingEffects(userTeam, noteOfChanges);
    }

    public List<String> userTeamTrainingEffects(Team userTeam, List<String> noteOfChanges) {
        noteOfChanges.add("Your team training effects:");
        var coaches = coachIntermediateService.findByTeam(userTeam);

        for (var coach : coaches) {
            if (coach.getPlayerOnTraining() != null) {
                var player = coach.getPlayerOnTraining();
                var program = coach.getCoachProgram();
                var playerTrainingBalance = player.getTrainingBalance();
                var playerTriningAble = player.getTrainingAble();
                var coachTrainingAble = coach.getTrainingAble();
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

    @Override
    @Transactional
    public List<String> setFinanceUpdates() {
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = getById(userTeamId);
        var day = dayIntermediateService.findByPresent();
        return setFinanceUpdates(userTeam, day);
    }

    public List<String> setFinanceUpdates(Team team, Day day) {
        var notesOfChanges = new ArrayList<String>();
        var teamSponsor = team.getSponsor();
        team.setWealth(team.getWealth().add(teamSponsor.getDayWage()));
        notesOfChanges.add("Sponsor " + teamSponsor.getName() + " gave your team " + teamSponsor.getDayWage() + " Euro. It is day wage.");

        if (day.isMatch()){
            team.setWealth(team.getWealth().add(teamSponsor.getMatchWage()));
            notesOfChanges.add("Sponsor " + teamSponsor.getName() + " gave your team " + teamSponsor.getMatchWage() + " Euro. It is match wage.");
        }

        return expensesUpdate(team, notesOfChanges, day);
    }

    private List<String> expensesUpdate(Team team, List<String> notesOfChanges, Day day) {
        var loans = bankIntermediateService.findByTeam(team);
        for (var x = 0; x < loans.size(); x++) {
            var dayOfWeek = day.getDate().getDayOfWeek();
            var loanDayOfWeek = loans.get(x).getDateOfLoan().getDate().getDayOfWeek();
            var dayOfMonth = day.getDate().getDayOfMonth();
            var loanDayOfMonth = loans.get(x).getDateOfLoan().getDate().getDayOfMonth();
            var finalPayment = new FinalPayment(false);

            if (loans.get(x).getTypeOfReturn().equals(Bank.TypeOfReturn.PER_DAY)) {
                notesOfChanges.addAll(bankIntermediateService.paymentPeriod(loans.get(x), Bank.TypeOfReturn.PER_DAY, team, finalPayment));
            }

            else if (loans.get(x).getTypeOfReturn().equals(Bank.TypeOfReturn.PER_WEEK)
                    && dayOfWeek.equals(loanDayOfWeek)) {
                notesOfChanges.addAll(bankIntermediateService.paymentPeriod(loans.get(x), Bank.TypeOfReturn.PER_WEEK, team, finalPayment));
            }

            else if(loans.get(x).getTypeOfReturn().equals(Bank.TypeOfReturn.PER_MONTH)
                    && dayOfMonth == loanDayOfMonth) {
                notesOfChanges.addAll(bankIntermediateService.paymentPeriod(loans.get(x), Bank.TypeOfReturn.PER_MONTH, team, finalPayment));
            }

            if (finalPayment.isFinal()) {
                x--;
            }
        }
        return notesOfChanges;
    }


    @Override
    @Transactional
    public List<String> setMarketingChanges() {
        return setMarketingChanges(dayIntermediateService.findByPresent());
    }

    public List<String> setMarketingChanges(Day day) {
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = getById(userTeamId);
        var notesOfChanges = new ArrayList<String>();
        var markets = marketIntermediateService.findByTeam(userTeam);
        var stadium = userTeam.getStadium();
        for (var x = 0; x < markets.size(); x++) {
            var capacity = stadium.getUsualAverageCapacity();
            var marketType = markets.get(x).getMarketType();
            var newFansValue = (int) (capacity + capacity / 100 * marketType.getCapacityCoeff());

            stadium.setUsualAverageCapacity(newFansValue);
            stadium.setSimpleCapacity(newFansValue);
            notesOfChanges.add("New Stadium capacity is " + stadium.getUsualAverageCapacity());
            var finishDay = markets.get(x).getFinishDate();
            if (finishDay.getDate().equals(day.getDate())){
                markets.remove(markets.get(x));
                log.info("Market program is finished");
                x--;
            }
        }
        return notesOfChanges;
    }

    @Override
    @Transactional
    public void setPlayerRecovery() {
        var teams = repository.findByUser(UserHolder.user);
        for (var t : teams) {
            for (var p : t.getPlayerList()) {
                if (p.getTire() > 0) {
                    p.setTire(p.getTire() - 5);
                }
            }
        }
    }

    @Override
    @Transactional
    public void removePlayerFromTeam(Team userTeam, Player player) {
        var playerList = userTeam.getPlayerList();
        playerList.remove(player);
        roleIntermediateService.setFreeFromRoleIfExists(userTeam, player);
        playerIntermediateService.remove(player);
    }
}
