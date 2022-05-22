package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sargassov.fmweb.dto.days_dtos.DayDto;
import ru.sargassov.fmweb.services.DayService;

@RestController
@AllArgsConstructor
@Slf4j
public class DayController {
    private final DayService dayService;

    @GetMapping("/dates")
    public DayDto getActualDate() {
        log.info("DayController.getActualDate");
        return dayService.getActualDateFromApi();
    }
}
