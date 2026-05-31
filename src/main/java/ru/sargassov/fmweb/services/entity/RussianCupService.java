package ru.sargassov.fmweb.services.entity;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.CupConverter;
import ru.sargassov.fmweb.exceptions.CupNotFoundException;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.repositories.entity.CupRepository;
import ru.sargassov.fmweb.spi.entity.RussianCupServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.CupIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.LeagueIntermediateServiceSpi;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class RussianCupService implements RussianCupServiceSpi {

    private static final long RUSSIAN_CUP_ID = 1L;
    private final CupIntermediateServiceSpi cupIntermediateService;
    private final LeagueIntermediateServiceSpi leagueIntermediateService;
    private final CupRepository cupRepository;
    private final CupConverter cupConverter;

    @Override
    @Transactional
    public void loadRussianCup(User user){
        log.info("LeagueService.getRussianCup");
        var cupEntity = cupRepository.findById(RUSSIAN_CUP_ID)
                .orElseThrow(() -> new CupNotFoundException(String.format("Cup '%s' not found", "RussianCupNumber")));
        var league = leagueIntermediateService.getLeague(user);
        var newCup = cupConverter.getIntermediateCupEntityFromEntity(cupEntity, user, league);
        cupIntermediateService.save(newCup);
    }
}
