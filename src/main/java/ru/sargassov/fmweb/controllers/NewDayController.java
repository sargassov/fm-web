package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sargassov.fmweb.dto.text_responses.TextResponse;
import ru.sargassov.fmweb.spi.NewDayServiceSpi;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class NewDayController {

    private final NewDayServiceSpi newDayService;

    @PostMapping("/new_day")
    public void createNewDay() {
        log.info("NewDayController.createNewDay");
//        newDayService.createNewDay();

    }

    @GetMapping("/new_day/changes")
    public List<TextResponse> loadChanges() {
        log.info("NewDayController.loadChanges");
//        return newDayService.loadChanges();
        return null;
    }
}
