package ru.sargassov.fmweb.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.intermediate_entities.Position;

import java.util.Optional;

@Repository
public interface PositionIntermediateRepository extends JpaRepository<Position, Long> {
    Optional<Position> findByTitle(String title);
}