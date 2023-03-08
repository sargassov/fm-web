package ru.sargassov.fmweb.repositories.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.intermediate_entities.Placement;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

@Repository
public interface PlacementIntermediateRepository extends JpaRepository<Placement, Long> {
    Placement findByNameAndUser(String title, User user);

    List<Placement> findByUser(User user);
}