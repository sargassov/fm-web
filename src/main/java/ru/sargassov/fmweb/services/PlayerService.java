package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api.UserApi;
import ru.sargassov.fmweb.converters.PlayerConverter;
import ru.sargassov.fmweb.dto.PlayerOnPagePlacementsDto;
import ru.sargassov.fmweb.intermediate_entites.Placement;
import ru.sargassov.fmweb.intermediate_entites.Player;
import ru.sargassov.fmweb.dto.PlayerOnPagePlayersDto;
import ru.sargassov.fmweb.intermediate_entites.Team;
import ru.sargassov.fmweb.repositories.PlayerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerConverter playerConverter;
    private final UserApi userApi;

    @SneakyThrows
    public List<Player> getAllPlayersByTeamId(Long id){
        if(id < 1)
            throw new Exception();

        return playerRepository.findAllByTeamId(id).stream()
                .map(playerConverter::entityToDto).collect(Collectors.toList());
    }

    public List<PlayerOnPagePlayersDto> getPlayerOnPagePlayersDtoFromPlayer(List<Player> playerList) {
        return playerList.stream()
                .map(playerConverter::getPlayerOnPagePlayersDtoFromPlayer)
                .collect(Collectors.toList());
    }

    public void resetAllStrategyPlaces(Team userTeam) {
        userTeam.getPlayerList().forEach(p -> p.setStrategyPlace(-100));
    }

    public PlayerOnPagePlayersDto getOnePlayerOnPagePlacementsDtoFromPlayer(String name){
        Player p = userApi.getPlayerByNameFromUserTeam(name);
        return playerConverter.getPlayerOnPagePlayersDtoFromPlayer(p);
    }

    public PlayerOnPagePlayersDto getAnotherPlayerByNumber(Integer number, int i) {
        Player p = null;

        do {
            try {
                p = userApi.getPlayerByNumberFromUserTeam(number += i);
            } catch (RuntimeException r){
                log.error("Player with number = " + number + " not found");
                if (number > 99 && i > 0) number = 0;
                if (number < 1 && i < 0) number = 100;
            }
//            if (p == null) {
//                log.error("Player with number = " + number + " not found");
//                if (number == 100) number = 0;
//                if (number == 0) number = 100;
//            }
        } while(p == null);

        return playerConverter.getPlayerOnPagePlayersDtoFromPlayer(p);
    }
}
