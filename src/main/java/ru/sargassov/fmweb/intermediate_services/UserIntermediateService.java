package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.dto.UserData;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_repositories.UserIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.UserIntermediateServiceSpi;

import javax.persistence.EntityNotFoundException;

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

    @Override
    public User findByUserData(UserData userData) {
        var login = userData.getName();
        var optUser = repository.findByName(login);
        return optUser.orElse(null);
    }
}
