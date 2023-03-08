package ru.sargassov.fmweb.spi.entity;

import ru.sargassov.fmweb.dto.IntegerDto;
import ru.sargassov.fmweb.dto.NameOfMonthDto;
import ru.sargassov.fmweb.dto.days_dtos.EventDto;

import java.util.List;

public interface CalendarServiceSpi {

    EventDto getTour(Integer parameter);
//
    List<EventDto> getMonth(Integer parameter);

    NameOfMonthDto getMonthName(Integer parameter);

//    Day getPresentDay();
//
//    Match findCurrentMatch(Team team, Team team1);
//
    IntegerDto getMonthParameter();
//
    IntegerDto getTourParameter();
}
