package ru.sargassov.fmweb.services.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_entities.Market;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.repositories.intermediate_repositories.MarketIntermediateRepository;
import ru.sargassov.fmweb.spi.intermediate_spi.MarketIntermediateServiceSpi;

import java.util.List;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class MarketIntermediateService implements MarketIntermediateServiceSpi {

    private MarketIntermediateRepository repository;
    @Override
    public Market save(Market market) {
        return repository.save(market);
    }

    @Override
    public List<Market> findByTeam(Team team) {
        return repository.findByTeam(team);
    }
}
