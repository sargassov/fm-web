package ru.sargassov.fmweb.spi.entity;

import ru.sargassov.fmweb.entities.DayEntity;
import ru.sargassov.fmweb.intermediate_entities.Day;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

public interface DayServiceSpi {

    List<DayEntity> findAll();

    List<Day> getDayIntermediateEntities(User user);

    void loadCalendar(User user);

//    List<Day> getCalendarFromApi();
//
//    DayDto getActualDateFromApi();
//
//    boolean isMatchday();
//
//    String addDate();
//
//    Day getActualDay();
}
