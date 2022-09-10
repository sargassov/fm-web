package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_entities.Draw;
import ru.sargassov.fmweb.intermediate_repositories.DrawIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.DrawIntermediateServiceSpi;

import java.util.List;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class DrawIntermediateService implements DrawIntermediateServiceSpi {
    private DrawIntermediateRepository repository;

    @Override
    public Draw save(Draw draw) {
        return repository.save(draw);
    }

    @Override
    public List<Draw> findAll() {
        return repository.findAll();
    }
}
