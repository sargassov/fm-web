package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.sargassov.fmweb.dto.NameOfMonthDto;
import ru.sargassov.fmweb.dto.days_dtos.EventDto;
import ru.sargassov.fmweb.services.CalendarService;

import java.security.spec.NamedParameterSpec;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CalendarController {
    private final CalendarService calendarService;

    @GetMapping("/calendar/league_calendar/{parameter}")
    public EventDto getTour(@PathVariable Integer parameter) {
        log.info("CalendarController.getTour");
        return calendarService.getTour(parameter);
    }

    @GetMapping("/calendar/month/{parameter}")
    public List<EventDto> getMonth(@PathVariable Integer parameter) {
        log.info("CalendarController.getMonth");
        return calendarService.getMonth(parameter);
    }

    @GetMapping("/calendar/month/{parameter}/name")
    public NameOfMonthDto getMonthName(@PathVariable Integer parameter) {
        log.info("CalendarController.getMonthName");
        return calendarService.getMonthName(parameter);
    }

}
