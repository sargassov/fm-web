package ru.sargassov.fmweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.entities.Placement;

@Repository
public interface PlacementRepository extends JpaRepository<Placement, Long> {
}
