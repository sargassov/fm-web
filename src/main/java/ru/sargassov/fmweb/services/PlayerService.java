package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.PlayerConverter;
import ru.sargassov.fmweb.dto.player_dtos.CreatedPlayerDto;
import ru.sargassov.fmweb.dto.PriceResponce;
import ru.sargassov.fmweb.dto.player_dtos.IdNamePricePlayerDto;
import ru.sargassov.fmweb.intermediate_entities.League;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.dto.player_dtos.PlayerSoftSkillDto;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.entity_repositories.PlayerRepository;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_spi.PlayerIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.PlayerServiceSpi;
import ru.sargassov.fmweb.validators.CreatedPlayersValidator;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PlayerService implements PlayerServiceSpi {
    private final PlayerRepository playerRepository;
    private final PlayerIntermediateServiceSpi playerIntermediateService;
    private final PlayerConverter playerConverter;

    @Override
    @SneakyThrows
    public List<Player> findAllByTeamEntityId(Long id, User user, League league){
        if(id < 1)
            throw new IllegalStateException("Wrong state of team entity id");

        var notSavedPlayers =  playerRepository.findAllByTeamId(id).stream()
                .map(pe -> playerConverter.getIntermediateEntityFromEntity(pe, user, league))
                .collect(Collectors.toList());
        return playerIntermediateService.save(notSavedPlayers);
    }

    @Override
    @Transactional
    public void resetAllStrategyPlaces(Team userTeam) {
        userTeam.getPlayerList().forEach(p -> p.setStrategyPlace(-100));
    }

//    @Override
//    @Transactional
//    public PlayerSoftSkillDto getOnePlayerOnPagePlacementsDtoFromPlayer(String name){
//        Player p = userService.getPlayerByNameFromUserTeam(name);
//        return playerConverter.getPlayerSoftSkillDtoFromIntermediateEntity(p);
//    }

//    @Override
//    @Transactional
//    public PlayerSoftSkillDto getAnotherPlayerByNumber(Integer number, int i) {
//        Player p = null;
//
//        do {
//            try {
//                p = userService.getPlayerByNumberFromUserTeam(number += i);
//            } catch (RuntimeException r){
//                log.error("Player with number = " + number + " not found");
//                if (number > 99 && i > 0) number = 0;
//                if (number < 1 && i < 0) number = 100;
//            }
//        } while(p == null);
//
//        return playerConverter.getPlayerSoftSkillDtoFromIntermediateEntity(p);
//    }



//    @Override
//    @Transactional
//    public List<PlayerOnTrainingDto> getPlayerOnTrainingDtoFromPlayer(List<Player> players) {
//        return players.stream()
//                .map(playerConverter::getPlayerOnTrainingDtoFromPlayer)
//                .collect(Collectors.toList());
//    }

    @Override
    public void guessPrice(Player p, User user) {
        playerConverter.guessPrice(p, user);
    }
}
