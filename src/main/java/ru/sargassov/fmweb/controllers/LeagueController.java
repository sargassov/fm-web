package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.sargassov.fmweb.dto.LeagueDto;
import ru.sargassov.fmweb.dto.matrix_dto.CortageDto;
import ru.sargassov.fmweb.dto.player_dtos.PlayerSoftSkillDto;
import ru.sargassov.fmweb.dto.team_dtos.TeamResultDto;
import ru.sargassov.fmweb.spi.intermediate_spi.TeamIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.entity.LeagueServiceSpi;

import java.util.List;


@RestController
@AllArgsConstructor
@Slf4j
public class LeagueController {
    private final LeagueServiceSpi leagueService;
    private final TeamIntermediateServiceSpi teamIntermediateService;

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

    @GetMapping("/league/results")
    public List<CortageDto> loadResultMatrix() {
        log.info("LeagueController.loadResultMatrix");
        return leagueService.loadResultMatrix();
    }

    @GetMapping("/league/players/{parameter}")
    public List<PlayerSoftSkillDto> loadPlayersSort(@PathVariable Integer parameter) {
        log.info("LeagueController.loadPlayersSort");
        return teamIntermediateService.loadPlayersSort(parameter);
    }
}
