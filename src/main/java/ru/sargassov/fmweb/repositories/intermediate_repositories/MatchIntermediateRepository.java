package ru.sargassov.fmweb.repositories.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.intermediate_entities.Match;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

@Repository
public interface MatchIntermediateRepository extends JpaRepository<Match, Long> {
    List<Match> findByUser(User user);

    List<Match> findByUserAndCountOfTour(User user, int countOfTour);

    Match findByHomeAndAwayAndUser(Team home, Team away, User user);
}