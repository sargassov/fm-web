package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.sargassov.fmweb.dto.CoachDto;
import ru.sargassov.fmweb.dto.PriceResponce;
import ru.sargassov.fmweb.intermediate_spi.CoachIntermediateServiceSpi;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CoachController {
    private final CoachIntermediateServiceSpi coachIntermediateService;

    @GetMapping("/coach/all")
    public List<CoachDto> getAllCoachFromUserTeam() {
        log.info("CoachController.getAllCoachFromUserTeam");
        return coachIntermediateService.getAllCoachFromUserTeam();
    }

    @GetMapping("/coach/new/responce")
    public void newCoachPurchaseResponce() {
        log.info("CoachController.newCoachPurchaseResponce");
        coachIntermediateService.newCoachPurchaseResponse();
    }

    @PostMapping("/coach/new/cost")
    public PriceResponce getPriceForNewCoach(@RequestBody CoachDto coachDto) {
        log.info("CoachController.getPriceForNewCoach");
        return coachIntermediateService.getPriceForNewCoach(coachDto);
    }

    @PostMapping("/coach/new/create")
    public void createNewCoach(@RequestBody CoachDto coachDto) {
        log.info("CoachController.createNewCoach");
        coachIntermediateService.createNewCoach(coachDto);
    }

    @PutMapping("/coach/player/change")
    public void changePlayerOnTrain(@RequestBody CoachDto coachDto) {
        log.info("CoachController.changePlayerOnTrain");
        coachIntermediateService.changePlayerOnTrain(coachDto);
    }

    @DeleteMapping("/coach/delete/{id}")
    public void deleteCoachById(@PathVariable Long id) {
        log.info("CoachController.deleteCoachByCount");
        coachIntermediateService.deleteByid(id);
    }

    @PutMapping("/coach/program/{id}/{program}")
    public void changeTrainingProgram(@PathVariable Long id, @PathVariable Integer program) {
        log.info("CoachController.changeTrainingProgram");
        coachIntermediateService.changeTrainingProgram(id, program);
    }

}
