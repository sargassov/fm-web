package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.exceptions.PlayerNotFoundException;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Role;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_repositories.TeamIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.RoleIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.TeamIntermediateServiceSpi;

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
    private final RoleIntermediateServiceSpi roleIntermediateService;

    @Override
    public List<Team> save(List<Team> newTeamsWithoutId) {
        return repository.saveAll(newTeamsWithoutId);
    }

    @Override
    public List<Team> findAll() {
        return repository.findAll();
    }

    @Override
    public Team save(Team team) {
        return repository.save(team);
    }

    @Override
    public void fillPlacementForAllTeams(User user) {
        findAll().
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
    public Team findByName(String s) {
        return repository.findByName(s);
    }

    @Override
    public List<Team> findAllSortedByName() {
        return findAll().stream()
                .sorted(Comparator.comparing(Team::getName))
                .collect(Collectors.toList());
    }

    @Override
    public void captainAppointment(Team team) {
        team.captainAppointment();
    }
    @Override
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
            roleIntermediateService.save(role);
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
}
