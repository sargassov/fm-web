package ru.sargassov.fmweb.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.intermediate_entities.Placement;

@Repository
public interface PlacementIntermediateRepository extends JpaRepository<Placement, Long> {
}