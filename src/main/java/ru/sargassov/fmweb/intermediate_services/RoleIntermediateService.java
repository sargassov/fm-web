package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.intermediate_entities.Placement;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Role;
import ru.sargassov.fmweb.intermediate_repositories.RoleIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.RoleIntermediateServiceSpi;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class RoleIntermediateService implements RoleIntermediateServiceSpi {
    private RoleIntermediateRepository repository;
    @Override
    public Role save(Role role) {
        return repository.save(role);
    }

    @Override
    public List<Role> save(ArrayList<Role> listRoles) {
        return repository.saveAll(listRoles);
    }

    @Override
    public void resetPlayerById(Long roleId) {
        repository.resetPlayerById(roleId);
    }

    @Override
    public Role findByPlayer(Player player) {
        return repository.findByPlayerAndUser(player, UserHolder.user);
    }

    @Override
    public void rolePlacementRelation(Placement placement) {
        for (var role : placement.getRoles()) {
            role.setPlacement(placement);
            save(role);
        }
    }

    @Override
    public List<Role> findByPlacement(Placement placement) {
        return repository.findByPlacement(placement);
    }
}
