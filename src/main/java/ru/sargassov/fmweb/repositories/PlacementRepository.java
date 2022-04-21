package ru.sargassov.fmweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.entities.PlacementEntity;

@Repository
public interface PlacementRepository extends JpaRepository<PlacementEntity, Long> {
}
