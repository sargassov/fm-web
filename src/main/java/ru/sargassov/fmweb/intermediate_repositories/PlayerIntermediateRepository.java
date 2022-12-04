package ru.sargassov.fmweb.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;

@Repository
public interface PlayerIntermediateRepository extends JpaRepository<Player, Long> {
    Player findByNameAndUser(String name, User user);

    Player findByTeamAndNumberAndUser(Team team, Integer integer, User user);

    Player findByNameAndTeamAndUser(String name, Team team, User user);

    @Modifying
    @Query("update Player p set p.strategyPlace = ?1 where p.name = ?2 and p.user = ?3")
    void resetStrategyPlaceByUserAndPlayerName(Integer resetStrategyPlace, String playerName, User user);
}