package ru.sargassov.fmweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.entities.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
