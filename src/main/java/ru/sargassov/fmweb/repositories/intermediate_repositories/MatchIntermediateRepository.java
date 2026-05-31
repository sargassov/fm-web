package ru.sargassov.fmweb.repositories.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.intermediate_entities.Match;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

@Repository
public interface MatchIntermediateRepository extends JpaRepository<Match, Long> {
    List<Match> findByUser(User user);

    @Query("""
           SELECT m FROM Match m WHERE m.user = ?1 AND m.countOfTour = ?2 AND m.isLeagueDay = TRUE
           """)
    List<Match> findByUserIdAndCountOfTourAndLeagueDay(User user, int countOfTour);

    @Query("""
           SELECT m FROM Match m WHERE m.user = ?1 AND m.countOfTour = ?2 AND m.isCupDay = TRUE
           """)
    List<Match> findByUserIdAndCountOfTourAndCupDay(User user, int countOfTour);

    @Query("""
           SELECT m FROM Match m WHERE m.home = ?1 AND m.away = ?2 AND m.user = ?3 AND m.isLeagueDay = TRUE
           """)
    Match findLeagueMatchesByHomeAndAwayAndUser(Team home, Team away, User user);
}