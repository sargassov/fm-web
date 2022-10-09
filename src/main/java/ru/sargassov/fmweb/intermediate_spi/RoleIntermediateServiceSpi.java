package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Role;

import java.util.ArrayList;
import java.util.List;

public interface RoleIntermediateServiceSpi {
    Role save(Role role);

    List<Role> save(ArrayList<Role> listRoles);

    void resetPlayerById(Long roleId);

    Role findByPlayer(Player player);
}
