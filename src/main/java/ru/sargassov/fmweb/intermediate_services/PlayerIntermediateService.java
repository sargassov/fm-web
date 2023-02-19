package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.converters.PlayerConverter;
import ru.sargassov.fmweb.dto.PlacementOnPagePlacementsDto;
import ru.sargassov.fmweb.dto.PriceResponce;
import ru.sargassov.fmweb.dto.player_dtos.CreatedPlayerDto;
import ru.sargassov.fmweb.dto.player_dtos.IdNamePricePlayerDto;
import ru.sargassov.fmweb.dto.player_dtos.PlayerSoftSkillDto;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_repositories.PlayerIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.PlayerIntermediateServiceSpi;
import ru.sargassov.fmweb.validators.CreatedPlayersValidator;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class PlayerIntermediateService implements PlayerIntermediateServiceSpi {

    private final PlayerIntermediateRepository repository;
    private final PlayerConverter playerConverter;
    private final CreatedPlayersValidator createdPlayersValidator;

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
            player = repository.findByTeamAndNumberAndUser(team, number += i, user);
            if (number > 99 && i > 0) number = 0;
            if (number < 1 && i < 0) number = 100;
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

    @Override
    @Transactional
    public PriceResponce guessNewPlayerCost(CreatedPlayerDto createdPlayerDto, User user) {
        createdPlayersValidator.newPlayervValidate(createdPlayerDto);

        PriceResponce cp = new PriceResponce();
        cp.setPrice("The price of the " + createdPlayerDto.getName() + " is " +
                playerConverter.getPriceOfIntermediateEntityFromCreatedDto(createdPlayerDto, user) + " mln $,");
        return cp;
    }

    @Override
    @Transactional
    public void createNewPlayer(CreatedPlayerDto createdPlayerDto) {
        createdPlayersValidator.newPlayervValidate(createdPlayerDto);
        Team team = UserHolder.user.getUserTeam();

        Player player = playerConverter.getIntermediateEntityFromCreatedDto(createdPlayerDto);
        createdPlayersValidator.teamEnoughCreditsValidate(player, team);
        team.setWealth(team.getWealth().subtract(player.getPrice()));
        team.substractTransferExpenses(player.getPrice());
        player.setUser(UserHolder.user);
        player.setTeam(team);
        player.setTrainingBalance(0);
        player.setTire(0);
        var savedPlayer = repository.save(player);
        team.getPlayerList().add(savedPlayer);
    }

    @Override
    @Transactional
    public IdNamePricePlayerDto getIdNamePricePlayerDtoFromPlayer(Player p) {
        return playerConverter.getIdNamePricePlayerDtoFromPlayer(p);
    }
}
