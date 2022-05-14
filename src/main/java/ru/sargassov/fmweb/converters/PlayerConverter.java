package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.api.TeamApi;
import ru.sargassov.fmweb.api.UserApi;
import ru.sargassov.fmweb.dto.CreatedPlayerDto;
import ru.sargassov.fmweb.dto.PlacementOnPagePlacementsDto;
import ru.sargassov.fmweb.dto.PlayerOnPagePlacementsDto;
import ru.sargassov.fmweb.intermediate_entites.Player;
import ru.sargassov.fmweb.dto.PlayerOnPagePlayersDto;
import ru.sargassov.fmweb.entities.PlayerEntity;
import ru.sargassov.fmweb.exceptions.TeamNotFoundException;
import ru.sargassov.fmweb.intermediate_entites.Position;
import ru.sargassov.fmweb.intermediate_entites.Team;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.Random;

@Component
@AllArgsConstructor
@Slf4j
public class PlayerConverter {
    private final PositionConverter positionConverter;
    private final PlayerPriceSetter playerPriceSetter;
    private final TeamApi teamApi;
    private final UserApi userApi;


    public Player getIntermediateEntityFromEntity(PlayerEntity playerEntity){

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
        pDto.guessTrainigAble();
        pDto.setPosition(positionConverter.entityToEnum(playerEntity.getPositionEntity().getTitle()));
        pDto.guessPower();
        pDto.setTeam(teamApi.getTeamApiList()
                .stream()
                .filter(t -> t.getId() == playerEntity.getTeamEntity().getId())
                .findFirst().orElseThrow(() ->
                        new TeamNotFoundException(String.format("Team with id = '%s' not found", playerEntity.getTeamEntity().getId()))));
        guessPrice(pDto);
        return pDto;
    }

    public PlayerOnPagePlayersDto getPlayerOnPagePlayersDtoFromIntermediateEntity(Player player) {
        PlayerOnPagePlayersDto pOnPageDto = new PlayerOnPagePlayersDto();
        pOnPageDto.setName(player.getName());
        pOnPageDto.setNatio(player.getNatio());
        pOnPageDto.setCaptain(booleanToString(player.isCapitan()));
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
        pOnPageDto.setPrice(player.getPrice());
        pOnPageDto.setNumber(player.getNumber());

        return pOnPageDto;
    }

    private String booleanToString(boolean capitan) {
        if(capitan) return "Captain";
        return "";
    }

    public PlayerOnPagePlacementsDto getPlayerOnPagePlacementsDtoFromIntermediateEntity(Player p) {
        PlayerOnPagePlacementsDto pOnPagePlDto = new PlayerOnPagePlacementsDto();
        pOnPagePlDto.setId(p.getId());
        pOnPagePlDto.setName(p.getName());
        pOnPagePlDto.setStrategyPlace(p.getStrategyPlace());
        pOnPagePlDto.setNumber(p.getNumber());
        pOnPagePlDto.setPower(p.getPower());
        pOnPagePlDto.setPosition(p.getPosition().toString());
        return pOnPagePlDto;
    }

    public Player getIntermediateEntityFromCreatedDto(CreatedPlayerDto createdPlayerDto) {
        Player p = new Player();
        p.setName(createdPlayerDto.getName());
        p.setNatio(createdPlayerDto.getNatio());
        p.setGkAble(createdPlayerDto.getGkAble());
        p.setDefAble(createdPlayerDto.getDefAble());
        p.setMidAble(createdPlayerDto.getMidAble());
        p.setForwAble(createdPlayerDto.getForwAble());
        p.setCaptainAble(createdPlayerDto.getCaptainAble());
        p.setBirthYear(createdPlayerDto.getBirthYear());
        p.guessPosition(createdPlayerDto.getPosition());
        p.setStrategyPlace(-100);
        guessPrice(p);
        p.guessPower();
        p.setTeam(userApi.getTeam());
        p.guessNumber(createdPlayerDto.getNumber());
        p.guessTrainigAble();
        return p;
    }

    public BigDecimal getPriceOfIntermediateEntityFromCreatedDto(CreatedPlayerDto createdPlayerDto) {
        Player p = new Player();
        p.setGkAble(createdPlayerDto.getGkAble());
        p.setDefAble(createdPlayerDto.getDefAble());
        p.setMidAble(createdPlayerDto.getMidAble());
        p.setForwAble(createdPlayerDto.getForwAble());
        p.setCaptainAble(createdPlayerDto.getCaptainAble());
        p.setBirthYear(createdPlayerDto.getBirthYear());
        p.guessPosition(createdPlayerDto.getPosition());
        guessPrice(p);
        return BigDecimal.valueOf(playerPriceSetter.createPrice(p)).setScale(2, RoundingMode.HALF_UP);
    }

    private void guessPrice(Player p){
        p.setPrice(BigDecimal.valueOf(playerPriceSetter.createPrice(p)).setScale(2, RoundingMode.HALF_UP));
    }




}
