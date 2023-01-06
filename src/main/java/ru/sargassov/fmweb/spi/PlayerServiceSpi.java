package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.intermediate_entities.League;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

public interface PlayerServiceSpi {

    List<Player> findAllByTeamEntityId(Long id, User user, League league);

    void resetAllStrategyPlaces(Team userTeam);

//    PlayerSoftSkillDto getOnePlayerOnPagePlacementsDtoFromPlayer(String name);
//
//    PlayerSoftSkillDto getAnotherPlayerByNumber(Integer number, int i);
//
//    void createNewPlayer(CreatedPlayerDto createdPlayerDto);

//    List<PlayerOnTrainingDto> getPlayerOnTrainingDtoFromPlayer(List<Player> players);

//    IdNamePricePlayerDto getIdNamePricePlayerDtoFromPlayer(Player p);

    void guessPrice(Player player, User user);
}
