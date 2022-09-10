package ru.sargassov.fmweb.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.intermediate_entities.days.TrainDay;

@Repository
public interface TrainDayIntermediateRepository extends JpaRepository<TrainDay, Long> {
}