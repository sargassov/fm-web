package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.dto.PlacementData;
import ru.sargassov.fmweb.dto.PlacementOnPagePlacementsDto;
import ru.sargassov.fmweb.intermediate_spi.DayIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.PlacementIntermediateServiceSpi;
import ru.sargassov.fmweb.services.PlacementService;

@RestController
@AllArgsConstructor
@Slf4j
public class PlacementController {
    private final PlacementIntermediateServiceSpi placementIntermediateService;
    private final PlacementService placementService;
    private final DayIntermediateServiceSpi dayIntermediateService;

    @GetMapping("/placement/current")
    public PlacementOnPagePlacementsDto getCurrentPlacementInfo() {
        log.info("PlacementController.getCurrentPlacementInfo()");
        return placementIntermediateService.getCurrentPlacementInfo();
    }

    @PostMapping("/placement/new")
    public void selectNewPlacement(@RequestBody PlacementData placementData) {
        log.info("PlacementController.selectNewPlacement()");
        var user = UserHolder.user;
        var userTeam = user.getUserTeam();
        placementService.findByPlacementData(placementData, userTeam, user);
    }

    @GetMapping("/placement/current/autoselect")
    public void autoFillCurrentPlacement() {
        log.info("PlacementController.autoFillCurrentPlacement");
        placementIntermediateService.autoFillCurrentPlacement(UserHolder.user.getUserTeam());
    }


    @DeleteMapping("/placement/delete/{name}")
    public void deletePlayerFromCurrentPlacement(@PathVariable String name) {
        log.info("PlacementController.deletePlayerFromCurrentPlacement");
        placementIntermediateService.deletePlayerFromCurrentPlacement(name);
    }

    @PostMapping("/placement/change_player/{name}")
    public void changePlayerInPlacement(@PathVariable String name) {
        log.info("PlacementController.changePlayerInPlacement");
        placementIntermediateService.changePlayerInPlacement(name);
    }

    @GetMapping("/placement/show_condition")
    public void getShowCondition() {
        log.info("PlacementController.getShowCondition");
        dayIntermediateService.getShowCondition();
    }
}
