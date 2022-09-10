package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Role;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

public interface TeamIntermediateServiceSpi {
    List<Team> save(List<Team> newTeamsWithoutId);

    List<Team> findAll();

    Team save(Team team);

    void fillPlacementForAllTeams(User user);

    void autoFillPlacement(Team team);

    List<Player> getSuitablePlayers(List<Player> playerList, Role role);

    Player findBest(List<Player> suitablePlayers);

    void captainAppointment(Team team);

    void powerTeamCounter(Team team);

    Team findByName(String s);

    List<Team> findAllSortedByName();
}
