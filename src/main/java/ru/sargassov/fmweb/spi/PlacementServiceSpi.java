package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.dto.PlacementData;
import ru.sargassov.fmweb.dto.PlacementOnPagePlacementsDto;
import ru.sargassov.fmweb.entities.PlacementEntity;
import ru.sargassov.fmweb.intermediate_entites.Placement;
import ru.sargassov.fmweb.intermediate_entites.Team;

import java.util.List;

public interface PlacementServiceSpi {

    List<PlacementEntity> findAllPlacements();

    void loadPlacements();

    void fillPlacementForAllTeams();

    void setPlacementsForAllTeams();

    void fillPlacement(Team team);

    List<Placement> getPlacementsFromPlacementApi();

    PlacementOnPagePlacementsDto getCurrentPlacementInfo();

    void setNewPlacement(PlacementData placementData);

    void autoFillCurrentPlacement();

    void deletePlayerFromCurrentPlacement(Integer number);
}
