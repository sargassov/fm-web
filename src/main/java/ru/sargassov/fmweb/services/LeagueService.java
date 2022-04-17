package ru.sargassov.fmweb.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.LeagueConverter;
import ru.sargassov.fmweb.dto.LeagueDto;
import ru.sargassov.fmweb.exceptions.LeagueNotFoundException;
import ru.sargassov.fmweb.repositories.LeagueRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LeagueService {
    private final LeagueRepository leagueRepository;
    private final LeagueConverter leagueConverter;
    private static final long RussianLeagueNumber = 1;

    public LeagueDto getRussianLeague(){
        log.info("LeagueService.getRussianLeague");
        return leagueConverter.entityToDto(leagueRepository.findById(RussianLeagueNumber)
                .orElseThrow(() -> new LeagueNotFoundException(String.format("League '%s' not found", "RussianLeagueNumber"))));
    }
}
