package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.dto.NameOfMonthDto;
import ru.sargassov.fmweb.dto.days_dtos.EventDto;
import ru.sargassov.fmweb.intermediate_entites.days.Day;

import java.util.List;

public interface CalendarServiceSpi {

    EventDto getTour(Integer parameter);

    List<EventDto> getMonth(Integer parameter);

    NameOfMonthDto getMonthName(Integer parameter);

    Day getPresentDay();
}
