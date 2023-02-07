package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.converters.PlacementConverter;
import ru.sargassov.fmweb.dto.PlacementData;
import ru.sargassov.fmweb.dto.PlacementOnPagePlacementsDto;
import ru.sargassov.fmweb.entity_repositories.PlacementRepository;
import ru.sargassov.fmweb.exceptions.PlayerNotFoundException;
import ru.sargassov.fmweb.form.TeamPlacementPowerForm;
import ru.sargassov.fmweb.intermediate_entities.Placement;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_repositories.PlacementIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.PlacementIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.RoleIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.TeamIntermediateServiceSpi;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class PlacementIntermediateService implements PlacementIntermediateServiceSpi {
    private final PlacementIntermediateRepository repository;
    private final PlacementRepository placementRepository;
    private final PlacementConverter placementConverter;
    private final TeamIntermediateServiceSpi teamIntermediateService;

    @Override
    public Placement save(Placement placement) {
        return repository.save(placement);
    }

    @Transactional
    @Override
    public PlacementOnPagePlacementsDto getCurrentPlacementInfo() {
        return placementConverter.getPlacementOnPagePlacementsDtoFromTeam();
    }

    @Override
    public void setNewPlacement(PlacementData placementData) {
        var placementTitle = placementData.getTitle();
        var user = UserHolder.user;
        var userTeam = user.getUserTeam();
        var placement = repository.findByNameAndUser(placementTitle, user);
        userTeam.setPlacement(placement);
        userTeam.resetTeamPower();
        userTeam.resetAllStrategyPlaces();
    }

    @Transactional
    @Override
    public void deletePlayerFromCurrentPlacement(String name) {
        var userTeam = UserHolder.user.getUserTeam();
        var userTeamPlacement = userTeam.getPlacement();
        var player = userTeam.findPlayerByName(name);
        player.resetStrategyPlace();

        for (var role : userTeamPlacement.getRoles()) {
            var rolePlayer = role.getPlayer();
            if (rolePlayer != null && rolePlayer.equals(player)) {
                role.setPlayer(null);
                break;
            }
        }
    }

    @Override
    @Transactional
    public void autoFillCurrentPlacement(Team team) {
        teamIntermediateService.autoFillPlacement(team);
        teamIntermediateService.powerTeamCounter(team);
    }

    @Override
    @Transactional
    public void changePlayerInPlacement(String name) {
        var userTeam = UserHolder.user.getUserTeam();
        var player = userTeam.findPlayerByName(name);
        var position = player.getPosition();
        var positionPlayerList = userTeam.findPlayersByPosition(position);
        var positionPlayerListNumbers = getPositionPlayersListNumbers(positionPlayerList, player);
        var playerNumber = player.getNumber();
        var anotherPlayerPositionNumber = defineAnotherPlayerPositionNumber(playerNumber, positionPlayerListNumbers);
        var changedPlayer = userTeam.findPlayerByNumber(anotherPlayerPositionNumber);
        var userPlacement = userTeam.getPlacement();

        for (var role : userPlacement.getRoles()) {
            if (role.getPlayer().equals(player)) {
                player.setStrategyPlace(-100);
                role.setPlayer(changedPlayer);
                changedPlayer.setStrategyPlace(role.getPosNumber());
                return;
            }
        }
        throw new PlayerNotFoundException("Player " + name + " not found in user placement");
    }

    private List<Integer> getPositionPlayersListNumbers(List<Player> positionPlayerList, Player currentPlayer) {
       return positionPlayerList.stream()
                .filter(p -> p.getStrategyPlace() < 0 || p.equals(currentPlayer))
                .sorted(Comparator.comparing(Player::getNumber))
                .map(pl -> pl.getNumber())
                .collect(Collectors.toList());
    }

    private Integer defineAnotherPlayerPositionNumber(Integer playerNumber, List<Integer> positionPlayerListNumbers) {
        Integer anotherPlayerPositionNumber = null;
        for (int x = 0; x < positionPlayerListNumbers.size(); x++) {
            if (positionPlayerListNumbers.get(x).equals(playerNumber)) {
                if (x < positionPlayerListNumbers.size() - 1) {
                    anotherPlayerPositionNumber = positionPlayerListNumbers.get(x + 1);
                } else {
                    anotherPlayerPositionNumber = positionPlayerListNumbers.get(0);
                }
                break;
            }
        }
        return anotherPlayerPositionNumber;
    }

    @Override
    public List<Placement> findByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    @Transactional
    public void optimalOpponentPlacement(Team team) {
        var allPlacements = placementRepository.findAll();
        var allIntermediatePlacements = allPlacements.stream()
                .map(pl -> placementConverter.getIntermediateEntityFromEntity(pl, team, UserHolder.user))
                .collect(Collectors.toList());
        var forms = new ArrayList<TeamPlacementPowerForm>();
        team.resetAllStrategyPlaces();
        for (var p : allIntermediatePlacements) {
            var desc = p.getName();
            save(p);
            setAndAutoFillCurrentPlacement(team, p);
            var currentPlacementTeamPower = team.getTeamPower();
            forms.add(new TeamPlacementPowerForm(desc, currentPlacementTeamPower, p));
            team.resetAllStrategyPlaces();
        }

        var optimalPlacement = forms.stream()
                .max(Comparator.comparing(TeamPlacementPowerForm::getCurrentPlacementTeamPower))
                .orElseThrow(); //todo обработать
        setAndAutoFillCurrentPlacement(team, optimalPlacement.getPlacement());
        teamIntermediateService.captainAppointment(team);
    }

    private void setAndAutoFillCurrentPlacement(Team team, Placement placement) {
        team.setPlacement(placement);
        autoFillCurrentPlacement(team);
    }
}
