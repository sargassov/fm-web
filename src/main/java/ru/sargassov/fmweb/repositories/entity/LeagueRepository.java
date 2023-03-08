package ru.sargassov.fmweb.repositories.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.entities.LeagueEntity;

@Repository
public interface LeagueRepository extends JpaRepository<LeagueEntity, Long> {
}
