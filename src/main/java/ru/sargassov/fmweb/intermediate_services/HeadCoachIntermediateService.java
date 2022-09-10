package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_entities.HeadCoach;
import ru.sargassov.fmweb.intermediate_repositories.HeadCoachIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.HeadCoachIntermediateServiceSpi;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class HeadCoachIntermediateService implements HeadCoachIntermediateServiceSpi {

    private final HeadCoachIntermediateRepository repository;
    @Override
    public HeadCoach save(HeadCoach headCoach) {
        return repository.save(headCoach);
    }
}
