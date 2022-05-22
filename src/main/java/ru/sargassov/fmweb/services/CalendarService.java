package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api_temporary_classes_group.CalendarApi;
import ru.sargassov.fmweb.converters.CalendarConverter;
import ru.sargassov.fmweb.dto.days_dtos.TourDto;

@Service
@AllArgsConstructor
public class CalendarService {
    private final CalendarApi calendarApi;
    private final CalendarConverter calendarConverter;

    public TourDto getAllTours(Integer parameter) {

        if (parameter == 0) parameter = 30;
        if (parameter == 31) parameter = 1;

        return calendarConverter.getTourDtoFromTourDayEntity(
                calendarApi.getTour(parameter));
    }
}
