package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.sargassov.fmweb.dto.text_responses.InformationDto;
import ru.sargassov.fmweb.spi.intermediate_spi.StadiumIntermediateServiceSpi;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class StadiumController {
    private final StadiumIntermediateServiceSpi stadiumIntermediateService;

    @GetMapping("/stadium/info")
    public List<InformationDto> getInfo() {
        log.info("StadiumController.getInfo");
        return stadiumIntermediateService.getInfo();
    }

    @GetMapping("/stadium/status_info")
    public List<InformationDto> getCurrentStatusInfo() {
        log.info("StadiumController.getCurrentStatusInfo");
        return stadiumIntermediateService.getCurrentStatusInfo();
    }

    @GetMapping("/stadium/ticket_cost/information")
    public List<InformationDto> getTicketCostInfo() {
        log.info("StadiumController.getTicketCostInfo");
        return stadiumIntermediateService.getTicketCostInfo();
    }

    @PostMapping("/stadium/ticket_cost/change")
    public void changeTicketCost(@RequestBody InformationDto dto) {
        log.info("StadiumController.changeTicketCost");
        stadiumIntermediateService.changeTicketCost(dto);
    }

    @GetMapping("/stadium/sectors/info")
    public List<InformationDto> getSplitSectorsInfo() {
        log.info("StadiumController.getSplitSectorsInfo");
        return stadiumIntermediateService.getSplitSectorsInfo();
    }

    @GetMapping("/stadium/sectors/info/all")
    public List<InformationDto> getSectorsCapacityInfo() {
        log.info("StadiumController.getSectorsCapacityInfo");
        return stadiumIntermediateService.getSectorsCapacityInfo();
    }

    @PutMapping("/stadium/sectors/change")
    public void changeSectorCapacity(@RequestBody InformationDto dto) {
        log.info("StadiumController.changeSectorCapacity");
        stadiumIntermediateService.changeSectorCapacity(dto);
    }

    @GetMapping("/team/markets/show")
    public Boolean getShowOtherMarketProgramsCondition() {
        log.info("StadiumController.getShowOtherMarketProgramsCondition");
        return stadiumIntermediateService.getShowOtherMarketProgramsCondition();
    }
}
