package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.sargassov.fmweb.dto.*;
import ru.sargassov.fmweb.dto.player_dtos.CreatedPlayerDto;
import ru.sargassov.fmweb.dto.player_dtos.PlayerSoftSkillDto;
import ru.sargassov.fmweb.services.PlayerService;

@RestController
@AllArgsConstructor
@Slf4j
public class PlayerController {
    private final PlayerService playerService;

    @GetMapping("/player/{name}")
    public PlayerSoftSkillDto getOnePlayerForIndividualPlayerPage(@PathVariable String name) {
        log.info("PlayerController.getPlayerForIndividualPlayerPage()");
        return playerService.getOnePlayerOnPagePlacementsDtoFromPlayer(name);
    }

    @GetMapping("/player/vision/next/{number}")
    public PlayerSoftSkillDto getNextPlayerByNumber(@PathVariable Integer number) {
        log.info("PlayerController.getNextPlayerByNumer()");
        return playerService.getAnotherPlayerByNumber(number, 1);
    }

    @GetMapping("/player/vision/prev/{number}")
    public PlayerSoftSkillDto getPrevPlayerByNumer(@PathVariable Integer number) {
        log.info("PlayerController.getPrevPlayerByNumer()");
        return playerService.getAnotherPlayerByNumber(number, -1);
    }

    @PostMapping("/player/new/create")
    public void createNewPlayer(@RequestBody CreatedPlayerDto createdPlayerDto) {
        log.info("PlayerController.createNewPlayer");
        playerService.createNewPlayer(createdPlayerDto);
    }

    @PostMapping("/player/new/cost")
    public PriceResponce guessNewPlayerCost(@RequestBody CreatedPlayerDto createdPlayerDto) {
        log.info("PlayerController.guessNewPlayerCost()");
        return playerService.guessNewPlayerCost(createdPlayerDto);
    }
}
