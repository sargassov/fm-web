package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.sargassov.fmweb.dto.TeamTransInformationDto;
import ru.sargassov.fmweb.dto.TextResponce;
import ru.sargassov.fmweb.dto.player_dtos.IdNamePricePlayerDto;
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

    @GetMapping("/team/name/next/{parameter}/{i}")
    public TeamOnPagePlayersDto getNextNameOfOpponentTeam(@PathVariable Integer parameter, @PathVariable Integer i) {
        log.info("TeamController.getNextNameOfOpponentTeam()");
        return teamService.getNameOfOpponentTeam(parameter, i);
    }

    @PutMapping("/team/{name}/players")
    public List<PlayerSoftSkillDto> getTenPlayersFromNextTeam(@PathVariable String name, @RequestBody TeamTransInformationDto teamDto) {
        log.info("TeamController.getTenPlayersFromNextByOtherTeam");
        return teamService.getTenPlayersFromNextTeam(name, teamDto.getPlayerParameter() + teamDto.getDelta(), teamDto.getSortParameter());
    }

    @PostMapping("/team/players/buy")
    public void buyNewPlayer(@RequestBody PlayerSoftSkillDto o) {
        log.info("TeamController.buyNewPlayer()");
        teamService.buyNewPlayer(o);
    }

    @DeleteMapping("/team/players/sell/{name}")
    public void sellPlayer(@PathVariable String name) {
        log.info("TeamController.sellPlayer()");
        teamService.sellPlayer(name);
    }

    @GetMapping("/team/players/selllist")
    public List<IdNamePricePlayerDto> getSellingList() {
        log.info("TeamController.getSellingList(parameter)");
        return teamService.getSellingList();
    }

}
