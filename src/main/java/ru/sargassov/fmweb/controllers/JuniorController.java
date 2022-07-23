package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sargassov.fmweb.dto.player_dtos.JuniorDto;
import ru.sargassov.fmweb.dto.text_responses.TextResponse;
import ru.sargassov.fmweb.services.JuniorService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class JuniorController {
    private final JuniorService juniorService;

    @GetMapping("/junior")
    private TextResponse isUserVisitedYouthAcademyToday(){
        return juniorService.isUserVisitedYouthAcademyToday();
    }

    @GetMapping("/junior/all_five")
    private List<JuniorDto> getRandomFiveYoungPlyers(){
        return juniorService.getRandomFiveYoungPlyers();
    }

    @PostMapping("/junior/new/create")
    private TextResponse invokeYoungPlayerInUserTeam(@RequestBody JuniorDto juniorDto){
        return juniorService.invokeYoungPlayerInUserTeam(juniorDto);
    }
}
