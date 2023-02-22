package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sargassov.fmweb.dto.UserData;
import ru.sargassov.fmweb.spi.GameServiceSpi;

@RestController
@AllArgsConstructor
@Slf4j
public class GameController {
    private final GameServiceSpi gameService;

    @PostMapping("/new_game")
    public void createNewGame(@RequestBody UserData userData) {
        log.info("GameController.createNewGame");
        gameService.createNewGame(userData);
    }

    @PostMapping("/load_game")
    public void loadGame(@RequestBody UserData userData) {
        log.info("GameController.loadGame");
        gameService.loadGame(userData);
    }
}
