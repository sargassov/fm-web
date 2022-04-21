package ru.sargassov.fmweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.entities.PositionEntity;

@Repository
public interface PositionRepository extends JpaRepository<PositionEntity, Long> {
}
