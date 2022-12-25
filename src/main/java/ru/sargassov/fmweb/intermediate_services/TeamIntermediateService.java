package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.comparators.TeamsPlayersComparators;
import ru.sargassov.fmweb.comparators.TrainingPlayersComparators;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.controllers.TeamController;
import ru.sargassov.fmweb.converters.PlayerConverter;
import ru.sargassov.fmweb.converters.TeamConverter;
import ru.sargassov.fmweb.dto.player_dtos.PlayerOnTrainingDto;
import ru.sargassov.fmweb.dto.player_dtos.PlayerSoftSkillDto;
import ru.sargassov.fmweb.dto.team_dtos.TeamOnPagePlayersDto;
import ru.sargassov.fmweb.exceptions.PlayerNotFoundException;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Role;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_repositories.TeamIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.PlayerIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.RoleIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.TeamIntermediateServiceSpi;
import ru.sargassov.fmweb.services.PlayerService;
import ru.sargassov.fmweb.spi.PlayerServiceSpi;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
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
        playerSoftSkillDtos.sort(teamsPlayersComparators.getComparators().get(parameter));
        return playerSoftSkillDtos;
    }

    private List<PlayerSoftSkillDto> getPlayerSoftSkillDtoFromPlayer(List<Player> playerList) {
        return playerList.stream()
                .map(p -> PlayerConverter.getPlayerSoftSkillDtoFromIntermediateEntity(p))
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
}
