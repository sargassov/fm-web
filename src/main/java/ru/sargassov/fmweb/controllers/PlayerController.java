package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.sargassov.fmweb.dto.*;
import ru.sargassov.fmweb.services.PlayerService;

@RestController
@AllArgsConstructor
@Slf4j
public class PlayerController {
    private final PlayerService playerService;

    @GetMapping("/player/{name}")
    public PlayerOnPagePlayersDto getOnePlayerForIndividualPlayerPage(@PathVariable String name) {
        log.info("PlayerController.getPlayerForIndividualPlayerPage()");
        return playerService.getOnePlayerOnPagePlacementsDtoFromPlayer(name);
    }

    @GetMapping("/player/next/{number}")
    public PlayerOnPagePlayersDto getNextPlayerByNumer(@PathVariable Integer number) {
        log.info("PlayerController.getNextPlayerByNumer()");
        return playerService.getAnotherPlayerByNumber(number, 1);
    }

    @GetMapping("/player/prev/{number}")
    public PlayerOnPagePlayersDto getPrevPlayerByNumer(@PathVariable Integer number) {
        log.info("PlayerController.getPrevPlayerByNumer()");
        return playerService.getAnotherPlayerByNumber(number, -1);
    }

    @PostMapping("/player/new/create")
    public void createNewPlayer(@RequestBody CreatedPlayerDto createdPlayerDto) {
        log.info("PlayerController.createNewPlayer");
        playerService.createNewPlayer(createdPlayerDto);
    }

    @PostMapping("/player/new/cost")
    public PlayersPriceOnPageCreatePlayerDto guessNewPlayerCost(@RequestBody CreatedPlayerDto createdPlayerDto) {
        log.info("PlayerController.guessNewPlayerCost()");
        return playerService.guessNewPlayerCost(createdPlayerDto);
    }
}
