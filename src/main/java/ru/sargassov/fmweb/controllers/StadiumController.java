package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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
        List<InformationDto> dto = stadiumService.getInfo();
            return dto;
    }


}
