package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.sargassov.fmweb.dto.SponsorDto;
import ru.sargassov.fmweb.dto.player_dtos.PlayerSoftSkillDto;
import ru.sargassov.fmweb.services.SponsorService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class SponsorController {
    private final SponsorService sponsorService;

    @GetMapping("/sponsor/all")
    public List<SponsorDto> gelAllSponsors() {
        log.info("SponsorController.gelAllSponsors()");
        return sponsorService.gelAllSponsors();
    }

    @PostMapping("/sponspor/change")
    public void changeSponsor(@RequestBody SponsorDto sponsorDto) {
        log.info("SponsorController.changeSponsor");
        sponsorService.changeSponsor(sponsorDto);
    }
}
