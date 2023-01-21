package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_entities.Market;
import ru.sargassov.fmweb.intermediate_repositories.MarketIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.MarketIntermediateServiceSpi;

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
}
