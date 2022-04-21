package ru.sargassov.fmweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.entities.TeamEntity;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
}
