package ru.sargassov.fmweb.entity_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.entities.PlacementEntity;

@Repository
public interface PlacementRepository extends JpaRepository<PlacementEntity, Long> {

    @Query("select count (pe) from PlacementEntity pe")
    Integer findAllPlacementsQuantity();
}

