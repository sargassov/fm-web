package ru.sargassov.fmweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.entities.Player;
import ru.sargassov.fmweb.entities.Team;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Query("select p from Player p where p.team.id = ?1")
    List<Player> findAllByTeamId(Long id);
}
