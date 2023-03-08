package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.sargassov.fmweb.dto.SponsorDto;;
import ru.sargassov.fmweb.spi.intermediate_spi.SponsorIntermediateServiceSpi;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class SponsorController {
    private final SponsorIntermediateServiceSpi sponsorIntermediateService;

    @GetMapping("/sponsor/all")
    public List<SponsorDto> gelAllSponsors() {
        log.info("SponsorController.gelAllSponsors()");
        return sponsorIntermediateService.gelAllSponsors();
    }

    @PostMapping("/sponspor/change")
    public void changeSponsor(@RequestBody SponsorDto sponsorDto) {
        log.info("SponsorController.changeSponsor");
        sponsorIntermediateService.changeSponsor(sponsorDto);
    }
}
