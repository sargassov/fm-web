package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_repositories.UserIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.UserIntermediateServiceSpi;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class UserIntermediateService implements UserIntermediateServiceSpi {
    private UserIntermediateRepository repository;
    @Override
    public User save(User user) {
        return repository.save(user);
    }
}
