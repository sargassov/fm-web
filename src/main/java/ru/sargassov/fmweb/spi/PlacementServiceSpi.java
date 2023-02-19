package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.dto.PlacementData;
import ru.sargassov.fmweb.entities.PlacementEntity;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

public interface PlacementServiceSpi {

    List<PlacementEntity> findAllPlacements();

    void loadPlacements(User user);

    void fillPlacementForAllTeams(User user);

    void setPlacementsForAllTeams(User user);

    void fillPlacement(Team team, User user, int quantity);

    void findByPlacementData(PlacementData placementData, Long teamId);

//    List<Placement> getPlacementsFromPlacementApi();
//
//    PlacementOnPagePlacementsDto getCurrentPlacementInfo();
//
//    void setNewPlacement(PlacementData placementData);
//
//    void autoFillCurrentPlacement();
//
//    void deletePlayerFromCurrentPlacement(Integer number);
}
