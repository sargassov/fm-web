package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_entities.Placement;
import ru.sargassov.fmweb.intermediate_repositories.PlacementIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.PlacementIntermediateServiceSpi;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class PlacementIntermediateService implements PlacementIntermediateServiceSpi {
    private final PlacementIntermediateRepository repository;
    @Override
    public Placement save(Placement placement) {
        return repository.save(placement);
    }
}
