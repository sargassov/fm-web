package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sargassov.fmweb.dto.LeagueDto;
import ru.sargassov.fmweb.dto.team_dtos.TeamResultDto;
import ru.sargassov.fmweb.spi.LeagueServiceSpi;

import java.util.List;


@RestController
@AllArgsConstructor
@Slf4j
public class LeagueController {
    private final LeagueServiceSpi leagueService;

    @GetMapping("/league/name")
    public LeagueDto getLeagueName() {
        log.info("LeagueController.getLeagueName");
        return leagueService.getLeagueName();
    }

    @GetMapping("/league/table")
    public List<TeamResultDto> loadTeamTable() {
        log.info("LeagueController.loadTeamTable");
        return leagueService.loadTeamTable();
    }
}
