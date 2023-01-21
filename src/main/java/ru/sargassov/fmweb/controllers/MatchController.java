package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sargassov.fmweb.dto.days_dtos.DayDto;
import ru.sargassov.fmweb.dto.text_responses.PostMatchDto;
import ru.sargassov.fmweb.dto.text_responses.PreMatchDto;
import ru.sargassov.fmweb.spi.MatchServiceSpi;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class MatchController {
    private final MatchServiceSpi matchService;

    @GetMapping("/match/pre_info")
    public List<PreMatchDto> getPreMatchInfo() {
        log.info("MatchController.getPreMatchInfo");
        return matchService.getPreMatchInfo();
    }

    @GetMapping("/match/post_info")
    public List<PostMatchDto> getPostMatchInfo() {
        log.info("MatchController.getPostMatchInfo");
        return matchService.getPostMatchInfo();
    }

    @PostMapping("/match/imitate")
    public void imitate(@RequestBody DayDto dayDto) {
        log.info("MatchController.getPreMatchInfo");
        matchService.imitate(dayDto);
    }


}
