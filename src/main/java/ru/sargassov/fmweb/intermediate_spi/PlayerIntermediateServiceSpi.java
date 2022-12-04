package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.dto.PlacementOnPagePlacementsDto;
import ru.sargassov.fmweb.dto.PriceResponce;
import ru.sargassov.fmweb.dto.player_dtos.CreatedPlayerDto;
import ru.sargassov.fmweb.dto.player_dtos.PlayerSoftSkillDto;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_repositories.PlayerIntermediateRepository;

import java.util.List;

public interface PlayerIntermediateServiceSpi {

    List<Player> save(List<Player> newPlayersWithoutIds);

    Player save(Player player);

    PlayerSoftSkillDto getOnePlayerOnPagePlacementsDtoFromPlayer(String name);

    PlayerSoftSkillDto getAnotherPlayerByNumber(Integer number, int i);

    Player findByNameAndTeamAndUser(String name, Team team, User user);

    void resetStrategyPlaceByPlayerName(String playerName);

    Player findByNameAndUser(String name, User user);

    PriceResponce guessNewPlayerCost(CreatedPlayerDto createdPlayerDto, User user);

    void createNewPlayer(CreatedPlayerDto createdPlayerDto);
}
