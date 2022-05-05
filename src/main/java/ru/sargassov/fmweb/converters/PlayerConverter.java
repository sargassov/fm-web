package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.api.TeamApi;
import ru.sargassov.fmweb.dto.League;
import ru.sargassov.fmweb.dto.Player;
import ru.sargassov.fmweb.dto.PlayerOnPagePlayersDto;
import ru.sargassov.fmweb.entities.PlayerEntity;
import ru.sargassov.fmweb.exceptions.TeamNotFoundException;
import ru.sargassov.fmweb.services.TeamService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import static java.math.BigDecimal.ROUND_DOWN;

@Component
@AllArgsConstructor
@Slf4j
public class PlayerConverter {
    private final PositionConverter positionConverter;
    private final PlayerPriceSetter playerPriceSetter;
    private final TeamApi teamApi;


    public Player entityToDto(PlayerEntity playerEntity){
        int trainingAbleValue = 20;
        int trainingAbleBottomValue = 10;
        Random random = new Random();

        Player pDto = new Player();
        pDto.setId(playerEntity.getId());
        pDto.setName(playerEntity.getName());
        pDto.setNatio(playerEntity.getNatio());
        pDto.setGkAble(playerEntity.getGkAble());
        pDto.setDefAble(playerEntity.getDefAble());
        pDto.setMidAble(playerEntity.getMidAble());
        pDto.setForwAble(playerEntity.getForwAble());
        pDto.setCaptainAble(playerEntity.getCaptainAble());
        pDto.setNumber(playerEntity.getNumber());
        pDto.setStrategyPlace(playerEntity.getStrategyPlace());
        pDto.setBirthYear(playerEntity.getBirthYear());
        pDto.setTrainingAble(random.nextInt(trainingAbleValue) + trainingAbleBottomValue);
        pDto.setPosition(positionConverter.entityToEnum(playerEntity.getPositionEntity().getTitle()));
        pDto.guessPower();
        pDto.setTeam(teamApi.getTeamApiList()
                .stream()
                .filter(t -> t.getId() == playerEntity.getTeamEntity().getId())
                .findFirst().orElseThrow(() ->
                        new TeamNotFoundException(String.format("Team with id = '%s' not found", playerEntity.getTeamEntity().getId()))));
        pDto.setPrice(playerPriceSetter.createPrice(pDto));
        return pDto;
    }

    public PlayerOnPagePlayersDto getPlayerOnPagePlayersDtoFromPlayer(Player player) {
        PlayerOnPagePlayersDto pOnPageDto = new PlayerOnPagePlayersDto();
        pOnPageDto.setId(player.getId());
        pOnPageDto.setName(player.getName());
        pOnPageDto.setNatio(player.getNatio());
        pOnPageDto.setClub(player.getTeam().getName());
        pOnPageDto.setPosition(player.getPosition().toString());
        pOnPageDto.setGkAble(player.getGkAble());
        pOnPageDto.setDefAble(player.getDefAble());
        pOnPageDto.setMidAble(player.getMidAble());
        pOnPageDto.setForwAble(player.getForwAble());
        pOnPageDto.setCaptainAble(player.getCaptainAble());
        pOnPageDto.setInjury(player.isInjury());
        pOnPageDto.setTrainingAble(player.getTrainingAble());
        pOnPageDto.setBirthYear(player.getBirthYear());
        pOnPageDto.setStrategyPlace(player.getStrategyPlace() + 1);
        pOnPageDto.setPower(player.getPower());
        pOnPageDto.setTimeBeforeTreat(player.getTimeBeforeTreat());
        pOnPageDto.setTire(player.getTire());
        pOnPageDto.setPrice(BigDecimal.valueOf(player.getPrice() / 1_000_000));
        pOnPageDto.setNumber(player.getNumber());

        return pOnPageDto;
    }
}
