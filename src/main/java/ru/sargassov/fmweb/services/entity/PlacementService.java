package ru.sargassov.fmweb.services.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.converters.PlacementConverter;
import ru.sargassov.fmweb.dto.PlacementData;
import ru.sargassov.fmweb.intermediate_entities.Placement;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.entities.PlacementEntity;
import ru.sargassov.fmweb.repositories.entity.PlacementRepository;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.spi.intermediate_spi.PlacementIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.PlayerIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.RoleIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.TeamIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.entity.PlacementServiceSpi;
import ru.sargassov.fmweb.spi.entity.PlayerServiceSpi;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Data
@Slf4j
public class PlacementService implements PlacementServiceSpi {
    private final PlacementRepository placementRepository;
    private final PlacementIntermediateServiceSpi placementIntermediateService;
    private final PlayerIntermediateServiceSpi playerIntermediateService;
    private final PlacementConverter placementConverter;
    private final TeamIntermediateServiceSpi teamIntermediateService;
    private final PlayerServiceSpi playerService;
    private final UserService userService;
    private final RoleIntermediateServiceSpi roleIntermediateService;

    @Override
    public List<PlacementEntity> findAllPlacements(){
        return placementRepository.findAll();
    }

    public Integer allPlacemntsQuantity(){
        return placementRepository.findAllPlacementsQuantity();
    }

    @Override
    @Transactional
    public void loadPlacements(User user) {
        log.info("PlacementService.loadPlacements");
        setPlacementsForAllTeams(user);
        fillPlacementForAllTeams(user);
    }

    @Override
    public void fillPlacementForAllTeams(User user) {
        teamIntermediateService.fillPlacementForAllTeams(user);
    }

    @Override
    @Transactional
    public void setPlacementsForAllTeams(User user) {
        int quantity = allPlacemntsQuantity();
        teamIntermediateService.findAllByUser(user).forEach(team -> fillPlacement(team, user, quantity));
    }

    @Override
    public void fillPlacement(Team team, User user, int quantity) {
        var selected = (long) (Math.random() * quantity) + 1;
        var optionalCurrentPlacementEntity = placementRepository.findById(selected);
        var savedPlacement = getIntermediateEntityAndSave(optionalCurrentPlacementEntity, team, user);
        team.setPlacement(savedPlacement);
        teamIntermediateService.save(team);
    }

    private Placement getIntermediateEntityAndSave(Optional<PlacementEntity> optionalCurrentPlacementEntity, Team team, User user) {
        if (optionalCurrentPlacementEntity.isPresent()) {
            var placementEntity = optionalCurrentPlacementEntity.get();
            var placement = placementConverter.getIntermediateEntityFromEntity(placementEntity, team, user);
            var savedPlacement = placementIntermediateService.save(placement);
            roleIntermediateService.rolePlacementRelation(savedPlacement);
            return savedPlacement;
        }
        throw new IllegalStateException("No match with current placement");
    }

    @Transactional
    public void findByPlacementData(PlacementData placementData, Long userTeamId) {
        var userTeam = teamIntermediateService.getById(userTeamId);
        var user = UserHolder.user;
        var title = placementData.getTitle();
        var entity = placementRepository.findByName(title);
        var placement = placementConverter.getIntermediateEntityFromEntity(entity, userTeam, user);
        placementIntermediateService.save(placement);
        userTeam.setPlacement(placement);
        userTeam.resetAllStrategyPlaces();
        teamIntermediateService.save(userTeam);
        user.setUserTeam(userTeam);
    }
}
