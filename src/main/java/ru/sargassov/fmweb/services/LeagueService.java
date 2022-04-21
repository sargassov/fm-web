package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.LeagueConverter;
import ru.sargassov.fmweb.dto.League;
import ru.sargassov.fmweb.exceptions.LeagueNotFoundException;
import ru.sargassov.fmweb.repositories.LeagueRepository;

@Service
@AllArgsConstructor
@Slf4j
public class LeagueService {
    private final LeagueRepository leagueRepository;
    private final LeagueConverter leagueConverter;
    private static final long RussianLeagueNumber = 1;

    public League getRussianLeague(){
        log.info("LeagueService.getRussianLeague");
        return leagueConverter.entityToDto(leagueRepository.findById(RussianLeagueNumber)
                .orElseThrow(() -> new LeagueNotFoundException(String.format("League '%s' not found", "RussianLeagueNumber"))));
    }
}
