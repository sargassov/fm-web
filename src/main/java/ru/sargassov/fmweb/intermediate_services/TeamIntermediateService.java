package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
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
import ru.sargassov.fmweb.intermediate_repositories.TeamIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.BankIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.DayIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.RoleIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.TeamIntermediateServiceSpi;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                forEach(t -> {
                    autoFillPlacement(t);
                    captainAppointment(t);
                    powerTeamCounter(t);
                });
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
        return teamConverter.dtoToTeamOnPagePlayersDto(UserHolder.user.getUserTeam());
    }

    @Override
    public List<PlayerSoftSkillDto> getAllPlayersByUserTeam(@NonNull Integer parameter) {
        log.info("TeamService.getAllPlayersByUserTeam()");
        var userTeam = UserHolder.user.getUserTeam();
        var playerList = userTeam.getPlayerList();
        var playerSoftSkillDtos = getPlayerSoftSkillDtoFromPlayer(playerList);
        playerSoftSkillDtos.sort(teamsPlayersComparators.getPlayerSoftSkillDtoComparators().get(parameter));
        return playerSoftSkillDtos;
    }

    private List<PlayerSoftSkillDto> getPlayerSoftSkillDtoFromPlayer(List<Player> playerList) {
        return playerList.stream()
                .map(PlayerConverter::getPlayerSoftSkillDtoFromIntermediateEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void captainAppointment(Team team) {
        team.captainAppointment();
    }

    @Override
    @Transactional
    public void autoFillPlacement(Team t) {
        log.info("TeamService.autoFillPlacement for " + t.getName());

        var healhtyPlayersSelect = healhtyPlayersSelect(t); // только здоровые игроки

        t.getPlacement().getRoles().forEach(role -> {
            log.info(t.getName() + " " + role.getTitle());
            var suitablePlayers = getSuitablePlayers(healhtyPlayersSelect, role).stream()
                    .filter(p -> p.getStrategyPlace() == -100)
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
        Player nowCap, futureCap;
        try{
            nowCap = getCaptainOfUserTeam();
            nowCap.setCapitan(false);
        }
        catch (RuntimeException r) {
            log.error("Captain function is not avalible");
        }
        futureCap = getPlayerByNameFromUserTeam(name);
        futureCap.setCapitan(true);
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
        var userTeam = user.getUserTeam();
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
            playerSoftSkillDtos.addAll(getPlayerSoftSkillDtoFromPlayer(teamPlayers));
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
        var userTeam = user.getUserTeam();
        for (var t : teams) {
            if (!t.equals(userTeam)) {
                noteOfChanges.addAll(t.setRandomTrainingEffects());
            }
        }
        return userTeam.userTeamTrainingEffects(noteOfChanges);
    }

    @Override
    @Transactional
    public List<String> setFinanceUpdates() {
        var userTeam = UserHolder.user.getUserTeam();
        var day = dayIntermediateService.findByPresent();
        return setFinanceUpdates(userTeam, day);
    }

    public List<String> setFinanceUpdates(Team team, Day day) {
        var notesOfChanges = new ArrayList<String>();
        var teamSponsor = team.getSponsor();
        team.setWealth(team.getWealth().add(teamSponsor.getDayWage()));
        notesOfChanges.add("Sponsor " + teamSponsor.getName() + " gave your team " + teamSponsor.getDayWage() + " Euro. It is day wage.");

        if(day.isMatch()){
            team.setWealth(team.getWealth().add(teamSponsor.getMatchWage()));
            notesOfChanges.add("Sponsor " + teamSponsor.getName() + " gave your team " + teamSponsor.getMatchWage() + " Euro. It is match wage.");
        }

        return expensesUpdate(team, notesOfChanges, day);
    }

    private List<String> expensesUpdate(Team team, List<String> notesOfChanges, Day day) {
        var loans = team.getLoans();
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
        var userTeam = UserHolder.user.getUserTeam();
        return userTeam.setMarketingChanges(dayIntermediateService.findByPresent());
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
}
