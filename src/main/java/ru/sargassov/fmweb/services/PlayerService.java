package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.PlayerConverter;
import ru.sargassov.fmweb.dto.PlayerOnPagePlacementsDto;
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
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerConverter playerConverter;

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

    public List<PlayerOnPagePlacementsDto> setEmptyBlankPlacement(Team userTeam) {
        int teamSize = 18;
        List<PlayerOnPagePlacementsDto> dtoList = new ArrayList<>(teamSize);
        for(int i = 0; i < teamSize; i++){
            final int finalI = i;
            Optional<Player> player = userTeam.getPlayerList().stream()
                    .filter(p -> p.getStrategyPlace() == finalI)
                    .findFirst();
            if(player.isPresent()) dtoList.add(playerConverter.getPlayerOnPagePlacementsDtoFromPlayer(player.get()));
            else dtoList.add(new PlayerOnPagePlacementsDto());
        }
        return dtoList;
    }
}
