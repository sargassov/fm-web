package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.PlayerConverter;
import ru.sargassov.fmweb.dto.player_dtos.CreatedPlayerDto;
import ru.sargassov.fmweb.dto.PriceResponce;
import ru.sargassov.fmweb.dto.player_dtos.PlayerOnTrainingDto;
import ru.sargassov.fmweb.intermediate_entites.Player;
import ru.sargassov.fmweb.dto.player_dtos.PlayerSoftSkillDto;
import ru.sargassov.fmweb.intermediate_entites.Team;
import ru.sargassov.fmweb.repositories.PlayerRepository;
import ru.sargassov.fmweb.validators.CreatedPlayersValidator;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerConverter playerConverter;
    private final UserService userService;
    private final CreatedPlayersValidator createdPlayersValidator;

    @SneakyThrows
    public List<Player> getAllPlayersByTeamId(Long id){
        if(id < 1)
            throw new Exception();

        return playerRepository.findAllByTeamId(id).stream()
                .map(playerConverter::getIntermediateEntityFromEntity).collect(Collectors.toList());
    }

    public List<PlayerSoftSkillDto> getPlayerSoftSkillDtoFromPlayer(List<Player> playerList) {
        return playerList.stream()
                .map(playerConverter::getPlayerSoftSkillDtoFromIntermediateEntity)
                .collect(Collectors.toList());
    }

    public void resetAllStrategyPlaces(Team userTeam) {
        userTeam.getPlayerList().forEach(p -> p.setStrategyPlace(-100));
    }

    public PlayerSoftSkillDto getOnePlayerOnPagePlacementsDtoFromPlayer(String name){
        Player p = userService.getPlayerByNameFromUserTeam(name);
        return playerConverter.getPlayerSoftSkillDtoFromIntermediateEntity(p);
    }

    public PlayerSoftSkillDto getAnotherPlayerByNumber(Integer number, int i) {
        Player p = null;

        do {
            try {
                p = userService.getPlayerByNumberFromUserTeam(number += i);
            } catch (RuntimeException r){
                log.error("Player with number = " + number + " not found");
                if (number > 99 && i > 0) number = 0;
                if (number < 1 && i < 0) number = 100;
            }
        } while(p == null);

        return playerConverter.getPlayerSoftSkillDtoFromIntermediateEntity(p);
    }

    public void createNewPlayer(CreatedPlayerDto createdPlayerDto) {
        createdPlayersValidator.newPlayervValidate(createdPlayerDto);
        Team team = userService.getUserTeam();

        Player player = playerConverter.getIntermediateEntityFromCreatedDto(createdPlayerDto);
        createdPlayersValidator.teamEnoughCreditsValidate(player, team);
        team.setWealth(team.getWealth().subtract(player.getPrice()));
        team.getPlayerList().add(player);
    }

    public PriceResponce guessNewPlayerCost(CreatedPlayerDto createdPlayerDto) {
        createdPlayersValidator.newPlayervValidate(createdPlayerDto);

        PriceResponce cp = new PriceResponce();
                cp.setPrice("The price of the " + createdPlayerDto.getName() + " is " +
                playerConverter.getPriceOfIntermediateEntityFromCreatedDto(createdPlayerDto) + " mln $,");
                return cp;
    }

    public List<PlayerOnTrainingDto> getPlayerOnTrainingDtoFromPlayer(List<Player> players) {
        return players.stream()
                .map(playerConverter::getPlayerOnTrainingDtoFromPlayer)
                .collect(Collectors.toList());
    }
}
