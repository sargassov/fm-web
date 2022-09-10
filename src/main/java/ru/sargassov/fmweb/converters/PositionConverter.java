package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.entities.PositionEntity;
import ru.sargassov.fmweb.intermediate_entities.Position;
import ru.sargassov.fmweb.intermediate_spi.PositionIntermediateServiceSpi;

@Component
@AllArgsConstructor
@Slf4j
public class PositionConverter {

    private final PositionIntermediateServiceSpi positionIntermediateService;
    public Position getIntermediateEntityFromEnum(PositionEntity positionEntity){
        var title = positionEntity.getTitle();
        var earlySavedPosition = positionIntermediateService.findByTitle(title);
        if (earlySavedPosition.isPresent()) {
            return earlySavedPosition.get();
        } else {
            var newPosition = new Position();
            newPosition.setTitle(title);
            newPosition.setPositionEntityId(positionEntity.getId());
            return positionIntermediateService.save(newPosition);
        }
    }
}
