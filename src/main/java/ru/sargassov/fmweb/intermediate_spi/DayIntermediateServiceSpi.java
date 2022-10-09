package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.dto.days_dtos.DayDto;
import ru.sargassov.fmweb.intermediate_entities.Day;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.ArrayList;
import java.util.List;

public interface DayIntermediateServiceSpi {
    Day save(Day d);

    List<Day> findByUser(User user);

    List<Day> save(List<Day> days);

    DayDto getActualDate();
}


