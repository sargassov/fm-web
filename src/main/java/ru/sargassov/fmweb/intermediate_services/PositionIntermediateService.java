package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_entities.Position;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_repositories.PositionIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.PositionIntermediateServiceSpi;

import java.util.List;
import java.util.Optional;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class PositionIntermediateService implements PositionIntermediateServiceSpi {

    private final PositionIntermediateRepository repository;
    @Override
    public Optional<Position> findByTitle(String title) {
        return repository.findByTitle(title);
    }

    @Override
    public Position save(Position newPosition) {
        return repository.save(newPosition);
    }

    @Override
    public List<Position> findAllByUser(User user) {
        return repository.findAllByUser(user);
    }

    @Override
    public List<Position> save(List<Position> positions) {
        return repository.saveAll(positions);
    }

    @Override
    public Position findByPositionEntityIdAndUser(Long positionEntityId, User user) {
        return repository.findByPositionEntityIdAndUser(positionEntityId, user);
    }
}
