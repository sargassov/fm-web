package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.sargassov.fmweb.dto.TextResponce;
import ru.sargassov.fmweb.dto.player_dtos.JuniorDto;
import ru.sargassov.fmweb.dto.player_dtos.PlayerOnTrainingDto;
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

    @GetMapping("/team/players_on_training/{parameter}")
    public List<PlayerOnTrainingDto> getAllPlayersOnTrainingByUserTeam(@PathVariable Integer parameter) {
        log.info("TeamController.getAllPlayersByUserTeam(parameter)");
        return teamService.getAllPlayersOnTrainingByUserTeam(parameter);
    }

    @GetMapping("/team/name/next/{parameter}")
    public TeamOnPagePlayersDto getNextNameOfOpponentTeam(@PathVariable Integer parameter) {
        log.info("TeamController.getNextNameOfOpponentTeam()");
        return teamService.getNameOfOpponentTeam(parameter, 1);
    }

    @GetMapping("/team/name/prev/{parameter}")
    public TeamOnPagePlayersDto getPrevNameOfOpponentTeam(@PathVariable Integer parameter) {
        log.info("TeamController.getPrevNameOfOpponentTeam()");
        return teamService.getNameOfOpponentTeam(parameter, - 1);
    }

    @PutMapping("/team/{name}/players/prev/{sortParameter}")
    public List<PlayerSoftSkillDto> getTenPlayersFromPrevByOtherTeam(@PathVariable String name, @PathVariable Integer sortParameter, @RequestBody Integer playerParameter) {
        log.info("TeamController.getTenPlayersFromPrevByOtherTeam");
        return teamService.getAllPlayersByOtherTeam(name, playerParameter - 1, sortParameter);
    }

    @PutMapping("/team/{name}/players/next/{sortParameter}")
    public List<PlayerSoftSkillDto> getTenPlayersFromNextByOtherTeam(@PathVariable String name, @PathVariable Integer sortParameter, @RequestBody Integer playerParameter) {
        log.info("TeamController.getTenPlayersFromNextByOtherTeam");
        return teamService.getAllPlayersByOtherTeam(name, playerParameter + 1, sortParameter);
    }

    @PutMapping("/team/userteam/players/prev/{sortParameter}")
    public List<PlayerSoftSkillDto> getTenPlayersFromPrevByUserTeam(@PathVariable Integer sortParameter, @RequestBody Integer playerParameter) {
        log.info("TeamController.getTenPlayersFromPrevByUserTeam");
        String name = teamService.getNameOfUserTeam().getName();
        return teamService.getAllPlayersByOtherTeam(name, playerParameter - 1, sortParameter);
    }

    @PutMapping("/team/userteam/players/next/{sortParameter}")
    public List<PlayerSoftSkillDto> getTenPlayersFromNextByUserTeam(@PathVariable Integer sortParameter, @RequestBody Integer playerParameter) {
        log.info("TeamController.getTenPlayersFromNextByUserTeam");
        String name = teamService.getNameOfUserTeam().getName();
        return teamService.getAllPlayersByOtherTeam(name, playerParameter + 1, sortParameter);
    }


}
