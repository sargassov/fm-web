package ru.sargassov.fmweb.spi.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.Placement;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Role;
import ru.sargassov.fmweb.intermediate_entities.Team;

import java.util.ArrayList;
import java.util.List;

public interface RoleIntermediateServiceSpi {
    Role save(Role role);

    List<Role> save(ArrayList<Role> listRoles);

    void resetPlayerById(Long roleId);

    Role findByPlayer(Player player);

    void rolePlacementRelation(Placement savedPlacement);

    List<Role> findByPlacement(Placement userPlacement);

    void setFreeFromRoleIfExists(Team userTeam, Player player);
}
