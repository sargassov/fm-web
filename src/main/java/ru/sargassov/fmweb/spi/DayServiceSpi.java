package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.dto.days_dtos.DayDto;
import ru.sargassov.fmweb.entities.DayEntity;
import ru.sargassov.fmweb.intermediate_entites.days.Day;

import java.util.List;

public interface DayServiceSpi {

    List<DayEntity> findAll();

    List<Day> convertAllToDto();

    void loadCalendar();

    List<Day> getCalendarFromApi();

    DayDto getActualDateFromApi();
}
