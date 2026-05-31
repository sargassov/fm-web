package ru.sargassov.fmweb.services.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_entities.Cup;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.repositories.intermediate_repositories.CupIntermediateRepository;
import ru.sargassov.fmweb.spi.intermediate_spi.CupIntermediateServiceSpi;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class CupIntermediateService implements CupIntermediateServiceSpi {

    private CupIntermediateRepository repository;

    @Override
    public Cup save(Cup cup) {
        return repository.save(cup);
    }

    @Override
    public Cup getCup(User user) {
        return repository.findByUser(user);
    }
}
