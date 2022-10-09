package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.dto.PlacementData;
import ru.sargassov.fmweb.dto.PlacementOnPagePlacementsDto;
import ru.sargassov.fmweb.intermediate_entities.Placement;

public interface PlacementIntermediateServiceSpi {
    Placement save(Placement placement);

    PlacementOnPagePlacementsDto getCurrentPlacementInfo();

    void setNewPlacement(PlacementData placementData);

    void deletePlayerFromCurrentPlacement(String name);

    void autoFillCurrentPlacement();

    void changePlayerInPlacement(String name);
}
