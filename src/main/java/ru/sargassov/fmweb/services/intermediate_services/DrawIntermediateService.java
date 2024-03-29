package ru.sargassov.fmweb.services.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_entities.Draw;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.repositories.intermediate_repositories.DrawIntermediateRepository;
import ru.sargassov.fmweb.spi.intermediate_spi.DrawIntermediateServiceSpi;

import java.util.ArrayList;
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
    public List<Draw> findAllByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    public List<Draw> save(ArrayList<Draw> drawList) {
        return repository.saveAll(drawList);
    }
}
