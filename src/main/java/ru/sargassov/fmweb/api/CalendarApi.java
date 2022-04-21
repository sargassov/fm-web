package ru.sargassov.fmweb.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.days.Day;
import ru.sargassov.fmweb.exceptions.PresentDayNotFoundException;
import ru.sargassov.fmweb.exceptions.SheduleInTourNotFoundException;
import ru.sargassov.fmweb.services.DayService;

import java.util.List;


@Component
@AllArgsConstructor
@Getter
public class CalendarApi {
    private List<Day> calendarApiList;

    public void setCalendarApiList(List<Day> calendarApiList) {
        this.calendarApiList = calendarApiList;
    }

    public Day getPresentDay(){
        return calendarApiList.stream()
                .filter(Day::isPresent)
                .findFirst()
                .orElseThrow(() ->
                        new PresentDayNotFoundException("Present day not found in calendar"));
    }
}
