package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.sargassov.fmweb.dto.InformationDto;
import ru.sargassov.fmweb.services.StadiumService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class StadiumController {
    private final StadiumService stadiumService;

    @GetMapping("/stadium/info")
    public List<InformationDto> getInfo() {
        log.info("StadiumController.getInfo");
        return stadiumService.getInfo();
    }

    @GetMapping("/stadium/status_info")
    public List<InformationDto> getCurrentStatusInfo() {
        log.info("StadiumController.getCurrentStatusInfo");
        return stadiumService.getCurrentStatusInfo();
    }

    @GetMapping("/stadium/ticket_cost/information")
    public List<InformationDto> getTicketCostInfo() {
        log.info("StadiumController.getTicketCostInfo");
        return stadiumService.getTicketCostInfo();
    }

    @PostMapping("/stadium/ticket_cost/change")
    public void changeTicketCost(@RequestBody InformationDto dto) {
        log.info("StadiumController.changeTicketCost");
        stadiumService.changeTicketCost(dto);
    }

    @GetMapping("/stadium/sectors/info")
    public List<InformationDto> getSplitSectorsInfo() {
        log.info("StadiumController.getSplitSectorsInfo");
        return stadiumService.getSplitSectorsInfo();
    }

    @GetMapping("/stadium/sectors/info/all")
    public List<InformationDto> getSectorsCapacityInfo() {
        log.info("StadiumController.getSectorsCapacityInfo");
        return stadiumService.getSectorsCapacityInfo();
    }

    @PutMapping("/stadium/sectors/change")
    public void changeSectorCapacity(@RequestBody InformationDto dto) {
        log.info("StadiumController.changeSectorCapacity");
        stadiumService.changeSectorCapacity(dto);
    }

}
