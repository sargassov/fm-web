package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.PositionConverter;
import ru.sargassov.fmweb.intermediate_entities.Position;
import ru.sargassov.fmweb.entity_repositories.PositionRepository;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_spi.LeagueIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.PositionIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.PositionServiceSpi;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PositionService implements PositionServiceSpi {
    private final PositionRepository positionRepository;
    private final PositionConverter positionConverter;
    private final PositionIntermediateServiceSpi positionIntermediateService;

    @Override
    @Transactional
    public List<Position> getAllPositions(){
        positionRepository.findAll();
        return null;
    }

    @Override
    public void loadPositions(User user) {
        var positionEntities = positionRepository.findAll();
        var notSavedPositions = positionEntities.stream()
                .map(positionEntity -> positionConverter.getIntermediateEntityFromEntity(positionEntity, user))
                .collect(Collectors.toList());
        positionIntermediateService.save(notSavedPositions);
    }
}
