package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sargassov.fmweb.dto.text_responses.TextResponse;
import ru.sargassov.fmweb.spi.CheatServiceSpi;

@RestController
@AllArgsConstructor
@Slf4j
public class CheatController {

    private CheatServiceSpi cheatService;

    @PostMapping("/cheat")
    public TextResponse activateCheat(@RequestBody TextResponse response){
        log.info("CheatController.getPriceForNewCoach");
        return cheatService.activateCheat(response);
    }
}
