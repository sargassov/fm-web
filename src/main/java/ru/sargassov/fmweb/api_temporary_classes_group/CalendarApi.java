package ru.sargassov.fmweb.api_temporary_classes_group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.constants.Constant;
import ru.sargassov.fmweb.exceptions.CalendarException;
import ru.sargassov.fmweb.intermediate_entites.days.Day;
import ru.sargassov.fmweb.exceptions.PresentDayNotFoundException;
import ru.sargassov.fmweb.intermediate_entites.days.TourDay;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
@AllArgsConstructor
@Getter
public class CalendarApi { //TEMPORARY CLASS
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

    public TourDay getTour(int parameter){
        return (TourDay) calendarApiList.stream()
                .filter(d -> d instanceof TourDay && ((TourDay) d).getCountOfTour() == parameter)
                .findFirst()
                .orElseThrow(() ->
                        new CalendarException("Tour #" + parameter + " was not found"));
    }

    public List<Day> getMonth(Integer parameter) {
        return calendarApiList.stream()
                .filter(d -> d.getDate().getMonth().toString()
                        .equals(Constant.Month.values()[parameter].toString()))
                .collect(Collectors.toList());
    }
}
