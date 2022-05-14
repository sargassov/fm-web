package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.sargassov.fmweb.dto.TextResponce;
import ru.sargassov.fmweb.dto.player_dtos.JuniorDto;
import ru.sargassov.fmweb.dto.player_dtos.PlayerSoftSkillDto;
import ru.sargassov.fmweb.dto.TeamOnPagePlayersDto;
import ru.sargassov.fmweb.services.TeamService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class TeamController {
    private final TeamService teamService;

    @GetMapping("/team/players/{parameter}")
    public List<PlayerSoftSkillDto> getAllPlayersByUserTeam(@PathVariable int parameter) {
        log.info("TeamController.getAllPlayersByUserTeam(parameter)");
        return teamService.getAllPlayersByUserTeam(parameter);
    }

    @GetMapping("/team/name")
    public TeamOnPagePlayersDto getNameOfUserTeam() {
        log.info("TeamController.getNameOfUserTeam()");
        return teamService.getNameOfUserTeam();
    }

    @PutMapping("/team/captain")
    public void setNewCaptain(@RequestBody String name) {
        log.info("TeamController.setNewCaptain()");
        teamService.setNewCaptainHandle(name);
    }


}
