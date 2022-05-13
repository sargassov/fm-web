package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.sargassov.fmweb.dto.PlacementOnPagePlacementsDto;
import ru.sargassov.fmweb.dto.PlayerOnPagePlacementsDto;
import ru.sargassov.fmweb.dto.PlayerOnPagePlayersDto;
import ru.sargassov.fmweb.services.PlayerService;
import ru.sargassov.fmweb.services.TeamService;

@RestController
@AllArgsConstructor
@Slf4j
public class PlayerController {
    private final PlayerService playerService;
    private final TeamService teamService;

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
}
