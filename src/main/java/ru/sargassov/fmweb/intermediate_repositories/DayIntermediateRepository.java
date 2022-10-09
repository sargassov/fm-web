package ru.sargassov.fmweb.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.dto.days_dtos.DayDto;
import ru.sargassov.fmweb.intermediate_entities.Day;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

@Repository
public interface DayIntermediateRepository extends JpaRepository<Day, Long> {

    List<Day> findByUser(User user);

    @Query("select d from Day d where d.isPresent = true and d.user = ?1")
    Day findByPresentIsTrueAnduser(User user);
}
