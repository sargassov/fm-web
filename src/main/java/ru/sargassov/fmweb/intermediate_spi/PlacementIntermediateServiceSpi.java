package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.dto.PlacementData;
import ru.sargassov.fmweb.dto.PlacementOnPagePlacementsDto;
import ru.sargassov.fmweb.intermediate_entities.Placement;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

public interface PlacementIntermediateServiceSpi {
    Placement save(Placement placement);

    PlacementOnPagePlacementsDto getCurrentPlacementInfo();

    void setNewPlacement(PlacementData placementData);

    void deletePlayerFromCurrentPlacement(String name);

    void autoFillCurrentPlacement(Team team, boolean matchPrepare);

    void changePlayerInPlacement(Integer place);

    List<Placement> findByUser(User user);

    void optimalOpponentPlacement(Team team);
}
