package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.sargassov.fmweb.dto.days_dtos.TourDto;
import ru.sargassov.fmweb.services.CalendarService;

@RestController
@AllArgsConstructor
@Slf4j
public class CalendarController {
    private final CalendarService calendarService;

    @GetMapping("/calendar/league_calendar/{parameter}")
    public TourDto getAllTours(@PathVariable Integer parameter) {
        log.info("CalendarController.getAllTours");
        return calendarService.getAllTours(parameter);
    }
}
