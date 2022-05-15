package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.sargassov.fmweb.dto.CoachDto;
import ru.sargassov.fmweb.dto.PriceResponce;
import ru.sargassov.fmweb.services.CoachService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CoachController {
    private final CoachService coachService;

    @GetMapping("/coach/all")
    public List<CoachDto> getAllCoachFromUserTeam() {
        log.info("CoachController.getAllCoachFromUserTeam");
        return coachService.getAllCoachFromUserTeam();
    }

    @GetMapping("/coach/new/responce")
    public void newCoachPurchaseResponce() {
        log.info("CoachController.newCoachPurchaseResponce");
        coachService.newCoachPurchaseResponce();
    }

    @PostMapping("/coach/new/cost")
    public PriceResponce getPriceForNewCoach(@RequestBody CoachDto coachDto){
        log.info("CoachController.getPriceForNewCoach");
        return coachService.getPriceForNewCoach(coachDto);
    }

    @PostMapping("/coach/new/create")
    public void createNewCoach(@RequestBody CoachDto coachDto){
        log.info("CoachController.createNewCoach");
        coachService.createNewCoach(coachDto);
    }

    @PutMapping("/coach/player/change")
    public void changePlayerOnTrain(@RequestBody CoachDto coachDto){
        log.info("CoachController.changePlayerOnTrain");
        coachService.changePlayerOnTrain(coachDto);
    }

    @DeleteMapping("/coach/delete/{count}")
    public void deleteCoachByCount(@PathVariable Integer count){
        log.info("CoachController.deleteCoachByCount");
        coachService.deleteCoachByCount(count);
    }
}
