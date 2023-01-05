package ru.sargassov.fmweb.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.dto.days_dtos.DayDto;
import ru.sargassov.fmweb.intermediate_entities.Day;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Repository
public interface DayIntermediateRepository extends JpaRepository<Day, Long> {

    List<Day> findByUser(User user);

    @Query("select d from Day d where d.isPresent = true and d.user = ?1")
    Day findByPresentIsTrueAnduser(User user);

    @Query("select d from Day d where d.isMatch = true and d.user = ?1")
    List<Day> loacAllMatchDates(User user);

    Day findByCountOfTourAndUser(Integer parameter, User user);

    @Query("select d from Day d where d.user = ?1")
    List<Day> getCalendar(User user);

    Day findByDateAndUser(LocalDate dayDate,User user);
}
