package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.LeagueConverter;
import ru.sargassov.fmweb.dto.LeagueDto;
import ru.sargassov.fmweb.intermediate_entites.League;
import ru.sargassov.fmweb.exceptions.LeagueNotFoundException;
import ru.sargassov.fmweb.repositories.LeagueRepository;
import ru.sargassov.fmweb.spi.LeagueServiceSpi;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class LeagueService implements LeagueServiceSpi {
    private final LeagueRepository leagueRepository;
    private final LeagueConverter leagueConverter;
    private static final long RussianLeagueNumber = 1;

    @Override
    @Transactional
    public League getRussianLeague(){
        log.info("LeagueService.getRussianLeague");
        return leagueConverter.getIntermediateEntityFromEntity(leagueRepository.findById(RussianLeagueNumber)
                .orElseThrow(() -> new LeagueNotFoundException(String.format("League '%s' not found", "RussianLeagueNumber"))));
    }

    @Override
    @Transactional
    public LeagueDto getLeagueName() {
        return leagueConverter.getLeagueDtoFromIntermediateEntity();
    }
}
