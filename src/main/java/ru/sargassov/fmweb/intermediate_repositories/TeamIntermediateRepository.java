package ru.sargassov.fmweb.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

@Repository
public interface TeamIntermediateRepository extends JpaRepository<Team, Long> {
    Team findByNameAndUser(String name, User user);
    Team findByTeamEntityIdAndUser(Long teamEntityId, User user);

    List<Team> findByUser(User user);
}