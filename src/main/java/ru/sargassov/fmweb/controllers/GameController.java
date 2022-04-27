package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sargassov.fmweb.dto.UserData;
import ru.sargassov.fmweb.services.GameService;

@RestController
@AllArgsConstructor
@Slf4j
public class GameController {
    private final GameService gameService;

    @PostMapping("/new_game")
    // необходим булеан
    public void createNewGame(@RequestBody UserData userData) {
        log.info("GameController.createNewGame");
        gameService.createNewGame(userData);
    }
//    @PostMapping("/new_user")
//    public void addUserInfo(@RequestBody UserData userData){
//
//    }

}
