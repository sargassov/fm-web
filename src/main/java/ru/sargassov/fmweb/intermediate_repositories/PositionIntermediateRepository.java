package ru.sargassov.fmweb.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.intermediate_entities.Position;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface PositionIntermediateRepository extends JpaRepository<Position, Long> {
    Optional<Position> findByTitleAndUser(String title, User user);

    Position findByPositionEntityIdAndUser(Long positionEntityId, User user);

    List<Position> findAllByUser(User user);
}