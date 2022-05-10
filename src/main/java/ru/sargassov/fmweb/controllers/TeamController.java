package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.sargassov.fmweb.dto.PlayerOnPagePlayersDto;
import ru.sargassov.fmweb.dto.TeamOnPagePlayersDto;
import ru.sargassov.fmweb.services.TeamService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class TeamController {
    private final TeamService teamService;

//    @GetMapping("/team/players")
//    public List<PlayerOnPagePlayersDto> getAllPlayersByUserTeam() {
//        log.info("TeamController.getAllPlayersByUserTeam()");
//        return teamService.getAllPlayersByUserTeam();
//    }

    @GetMapping("/team/players/{parameter}")
    public List<PlayerOnPagePlayersDto> getAllPlayersByUserTeam(@PathVariable int parameter) {
        log.info("TeamController.getAllPlayersByUserTeam(parameter)");
        return teamService.getAllPlayersByUserTeam(parameter);
    }

    @GetMapping("/team/name")
    public TeamOnPagePlayersDto getNameOfUserTeam() {
        log.info("TeamController.getNameOfUserTeam()");
        return teamService.getNameOfUserTeam();
    }
}
