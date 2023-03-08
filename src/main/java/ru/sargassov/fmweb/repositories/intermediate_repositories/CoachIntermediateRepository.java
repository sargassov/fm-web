package ru.sargassov.fmweb.repositories.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.intermediate_entities.Coach;
import ru.sargassov.fmweb.intermediate_entities.Team;

import java.util.List;

@Repository
public interface CoachIntermediateRepository extends JpaRepository<Coach, Long> {

    List<Coach> findByTeam(Team team);
}