package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sargassov.fmweb.dto.LeagueDto;
import ru.sargassov.fmweb.services.LeagueService;


@RestController
@AllArgsConstructor
@Slf4j
public class LeagueController {
    private final LeagueService leagueService;

    @GetMapping("/league/name")
    public LeagueDto getLeagueName() {
        log.info("LeagueController.getLeagueName");
        return leagueService.getLeagueName();
    }
}
