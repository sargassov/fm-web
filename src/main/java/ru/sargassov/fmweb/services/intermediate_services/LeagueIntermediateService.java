package ru.sargassov.fmweb.services.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_entities.League;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.repositories.intermediate_repositories.LeagueIntermediateRepository;
import ru.sargassov.fmweb.spi.intermediate_spi.LeagueIntermediateServiceSpi;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class LeagueIntermediateService implements LeagueIntermediateServiceSpi {

    private LeagueIntermediateRepository repository;
    @Override
    public void save(League league) {
        repository.save(league);
    }

    @Override
    public League getLeague(User user) {
        return repository.findByUser(user);
    }
}
