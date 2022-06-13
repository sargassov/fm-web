package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api_temporary_classes_group.CalendarApi;
import ru.sargassov.fmweb.constants.Constant;
import ru.sargassov.fmweb.converters.CalendarConverter;
import ru.sargassov.fmweb.dto.NameOfMonthDto;
import ru.sargassov.fmweb.dto.days_dtos.EventDto;
import ru.sargassov.fmweb.intermediate_entites.days.Day;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CalendarService {
    private final CalendarApi calendarApi;
    private final CalendarConverter calendarConverter;

    public EventDto getTour(Integer parameter) {
        if (parameter == 0) parameter = 30;
        if (parameter == 31) parameter = 1;
        return calendarConverter.getEventDtoFromTourDayEntity(
                calendarApi.getTour(parameter));
    }

    public List<EventDto> getMonth(Integer parameter) {
        if (parameter < 0) parameter = 10;
        if (parameter > 10) parameter = 0;
        return calendarApi.getMonth(parameter).stream()
                .map(calendarConverter::getEventDtoFromDayEntity)
                .collect(Collectors.toList());
    }

    public NameOfMonthDto getMonthName(Integer parameter) {
        if(parameter == -1) parameter = 10;
        if(parameter == 11) parameter = 0;
        String s = Constant.Month.values()[parameter].toString();
        return calendarConverter.getNameOfMonthDtoFromConstant(s);
    }

    public Day getPresentDay() {
        return calendarApi.getPresentDay();
    }
}
