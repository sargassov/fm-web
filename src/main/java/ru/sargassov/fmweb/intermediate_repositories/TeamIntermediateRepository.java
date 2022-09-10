package ru.sargassov.fmweb.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.intermediate_entities.Team;

@Repository
public interface TeamIntermediateRepository extends JpaRepository<Team, Long> {
    Team findByName(String s);
}