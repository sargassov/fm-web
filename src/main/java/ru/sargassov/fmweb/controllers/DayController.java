package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sargassov.fmweb.dto.days_dtos.DayDto;
import ru.sargassov.fmweb.intermediate_spi.DayIntermediateServiceSpi;

@RestController
@AllArgsConstructor
@Slf4j
public class DayController {
    private DayIntermediateServiceSpi dayIntermediateService;

    @GetMapping("/dates")
    public DayDto getActualDate() {
        log.info("DayController.getActualDate");
        return dayIntermediateService.getActualDate();
    }
}
