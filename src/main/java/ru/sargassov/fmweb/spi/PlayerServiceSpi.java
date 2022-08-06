package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.dto.PriceResponce;
import ru.sargassov.fmweb.dto.player_dtos.CreatedPlayerDto;
import ru.sargassov.fmweb.dto.player_dtos.IdNamePricePlayerDto;
import ru.sargassov.fmweb.dto.player_dtos.PlayerOnTrainingDto;
import ru.sargassov.fmweb.dto.player_dtos.PlayerSoftSkillDto;
import ru.sargassov.fmweb.intermediate_entites.Player;
import ru.sargassov.fmweb.intermediate_entites.Team;

import java.util.List;

public interface PlayerServiceSpi {

    List<Player> getAllPlayersByTeamId(Long id);

    List<PlayerSoftSkillDto> getPlayerSoftSkillDtoFromPlayer(List<Player> playerList);

    void resetAllStrategyPlaces(Team userTeam);

    PlayerSoftSkillDto getOnePlayerOnPagePlacementsDtoFromPlayer(String name);

    PlayerSoftSkillDto getAnotherPlayerByNumber(Integer number, int i);

    void createNewPlayer(CreatedPlayerDto createdPlayerDto);

    PriceResponce guessNewPlayerCost(CreatedPlayerDto createdPlayerDto);

    List<PlayerOnTrainingDto> getPlayerOnTrainingDtoFromPlayer(List<Player> players);

    IdNamePricePlayerDto getIdNamePricePlayerDtoFromPlayer(Player p);

    void guessPrice(Player player);
}
