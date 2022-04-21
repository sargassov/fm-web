package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sargassov.fmweb.services.GameService;

@RestController
@AllArgsConstructor
@Slf4j
public class GameController {
    private final GameService gameService;

    @GetMapping("/new")
    public Object createNewGame() {
        log.info("GameController.createNewGame");
        return gameService.createNewGame();
    }
}
