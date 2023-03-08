package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.dto.PlacementData;
import ru.sargassov.fmweb.dto.PlacementOnPagePlacementsDto;
import ru.sargassov.fmweb.spi.intermediate_spi.DayIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.PlacementIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.TeamIntermediateServiceSpi;
import ru.sargassov.fmweb.services.entity.PlacementService;

@RestController
@AllArgsConstructor
@Slf4j
public class PlacementController {
    private final PlacementIntermediateServiceSpi placementIntermediateService;
    private final PlacementService placementService;
    private final DayIntermediateServiceSpi dayIntermediateService;
    private final TeamIntermediateServiceSpi teamIntermediateService;

    @GetMapping("/placement/current")
    public PlacementOnPagePlacementsDto getCurrentPlacementInfo() {
        log.info("PlacementController.getCurrentPlacementInfo()");
        return placementIntermediateService.getCurrentPlacementInfo();
    }

    @PostMapping("/placement/new")
    public void selectNewPlacement(@RequestBody PlacementData placementData) {
        log.info("PlacementController.selectNewPlacement()");
        var userTeamId = UserHolder.user.getUserTeam().getId();
        placementService.findByPlacementData(placementData, userTeamId);
    }

    @GetMapping("/placement/current/autoselect")
    public void autoFillCurrentPlacement() {
        log.info("PlacementController.autoFillCurrentPlacement");
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var team = teamIntermediateService.getById(userTeamId);
        placementIntermediateService.autoFillCurrentPlacement(team, false);
    }

    @DeleteMapping("/placement/delete/{name}")
    public void deletePlayerFromCurrentPlacement(@PathVariable String name) {
        log.info("PlacementController.deletePlayerFromCurrentPlacement");
        placementIntermediateService.deletePlayerFromCurrentPlacement(name);
    }

    @PostMapping("/placement/change_player/{place}")
    public void changePlayerInPlacement(@PathVariable Integer place) {
        log.info("PlacementController.changePlayerInPlacement");
        placementIntermediateService.changePlayerInPlacement(place);
    }

    @GetMapping("/placement/show_condition")
    public Boolean showConditionResponse() {
        log.info("PlacementController.showConditionResponse");
        return dayIntermediateService.getShowCondition();
    }
}
