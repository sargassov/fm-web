package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.dto.player_dtos.PlayerOnTrainingDto;
import ru.sargassov.fmweb.dto.player_dtos.PlayerSoftSkillDto;
import ru.sargassov.fmweb.dto.team_dtos.TeamOnPagePlayersDto;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Role;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;
import java.util.Optional;

public interface TeamIntermediateServiceSpi {
    List<Team> save(List<Team> newTeamsWithoutId);

    List<Team> findAllByUser(User user);

    Team save(Team team);

    void fillPlacementForAllTeams(User user);

    void autoFillPlacement(Team team);

    List<Player> getSuitablePlayers(List<Player> playerList, Role role);

    Player findBest(List<Player> suitablePlayers);

    void captainAppointment(Team team);

    void powerTeamCounter(Team team);

    Team findByNameAndUser(String name, User user);

    List<Team> findAllByUserSortedByName(User user);

    Team findByTeamEntityIdAndUser(Long teamEntityId, User user);

    TeamOnPagePlayersDto getNameOfUserTeam();

    List<PlayerSoftSkillDto> getAllPlayersByUserTeam(Integer parameter);

    void setNewCaptainHandle(String name);

    Team getById(Long teamId);

    List<PlayerSoftSkillDto> loadPlayersSort(Integer parameter);

    List<String> setTrainingEffects();

    List<String> setFinanceUpdates();

    List<String> setMarketingChanges();

    void setPlayerRecovery();
}
