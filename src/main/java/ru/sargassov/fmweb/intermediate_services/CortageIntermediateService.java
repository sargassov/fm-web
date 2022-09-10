package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_entities.Cortage;
import ru.sargassov.fmweb.intermediate_repositories.CortageIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.CortageIntermediateServiceSpi;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class CortageIntermediateService implements CortageIntermediateServiceSpi {

    private CortageIntermediateRepository repository;

    @Override
    public List<Cortage> constructMatrix() {
        return new ArrayList<Cortage>(16);
    }

    @Override
    public Cortage save(Cortage cortage) {
        return repository.save(cortage);
    }
}
