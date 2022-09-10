package ru.sargassov.fmweb.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.intermediate_entities.Day;

import java.util.List;

@Repository
public interface DayIntermediateRepository extends JpaRepository<Day, Long> {
    @Query("select day from Day day where day.isMatch = true")
    List<Day> findAllWhereIsMatchTrue();
}
