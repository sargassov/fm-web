package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.sargassov.fmweb.dto.PlacementData;
import ru.sargassov.fmweb.dto.PlacementOnPagePlacementsDto;
import ru.sargassov.fmweb.services.PlacementService;

@RestController
@AllArgsConstructor
@Slf4j
public class PlacementController {
    private final PlacementService placementService;

    @GetMapping("/placement/current")
    public PlacementOnPagePlacementsDto getCurrentPlacementInfo() {
        log.info("PlacementController.getCurrentPlacementInfo()");
        return placementService.getCurrentPlacementInfo();
    }

    @PostMapping("/placement/new")
    public void selectNewPlacement(@RequestBody PlacementData placementData) {
        log.info("PlacementController.selectNewPlacement()");
        placementService.setNewPlacement(placementData);
    }

    @GetMapping("/placement/current/autoselect")
    public void autoFillCurrentPlacement() {
        log.info("PlacementController.getCurrentPlacementInfo()");
        placementService.autoFillCurrentPlacement();
    }


    @DeleteMapping("/placement/delete/{number}")
    public void deletePlayerFromCurrentPlacement(@PathVariable Integer number) {
        log.info("PlacementController.getCurrentPlacementInfo()");
        placementService.deletePlayerFromCurrentPlacement(number);
    }

}
