package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.converters.PlayerConverter;
import ru.sargassov.fmweb.dto.PlacementOnPagePlacementsDto;
import ru.sargassov.fmweb.dto.player_dtos.PlayerSoftSkillDto;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_repositories.PlayerIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.PlayerIntermediateServiceSpi;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class PlayerIntermediateService implements PlayerIntermediateServiceSpi {

    private final PlayerIntermediateRepository repository;
    private final PlayerConverter playerConverter;

    @Override
    public List<Player> save(List<Player> newPlayersWithoutIds) {
        return repository.saveAll(newPlayersWithoutIds);
    }

    @Override
    public Player save(Player player) {
        return repository.save(player);
    }

    @Override
    public PlayerSoftSkillDto getOnePlayerOnPagePlacementsDtoFromPlayer(String name) {
        var player = repository.findByNameAndUser(name, UserHolder.user);
        return PlayerConverter.getPlayerSoftSkillDtoFromIntermediateEntity(player);
    }

    @Override
    public PlayerSoftSkillDto getAnotherPlayerByNumber(Integer number, int i) {
        Player player = null;
        var user = UserHolder.user;
        var team = user.getUserTeam();

        do {
            try {
                player = repository.findByTeamAndNumberAndUser(team, number += i, user);
            } catch (RuntimeException r) {
                log.error("Player with number = " + number + " not found");
                if (number > 99 && i > 0) number = 0;
                if (number < 1 && i < 0) number = 100;
            }
        } while (player == null);

        return PlayerConverter.getPlayerSoftSkillDtoFromIntermediateEntity(player);
    }

    @Override
    public Player findByNameAndTeamAndUser(String name, Team userTeam, User user) {
        return repository.findByNameAndTeamAndUser(name, userTeam, user);
    }

    @Override
    public void resetStrategyPlaceByPlayerName(String playerName) {
        var user = UserHolder.user;
        var resetStrategyPlace = -100;
        repository.resetStrategyPlaceByUserAndPlayerName(resetStrategyPlace, playerName, user);
    }

    @Override
    public Player findByNameAndUser(String name, User user) {
        return repository.findByNameAndUser(name, user);
    }


    public PlayerIntermediateRepository getRepository() {
        return repository;
    }
}
