package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_entities.Role;
import ru.sargassov.fmweb.intermediate_repositories.RoleIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.RoleIntermediateServiceSpi;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class RoleIntermediateService implements RoleIntermediateServiceSpi {
    private RoleIntermediateRepository repository;
    @Override
    public Role save(Role role) {
        return repository.save(role);
    }
}
