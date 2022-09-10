package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.api_temporary_classes_group.TeamApi;
import ru.sargassov.fmweb.api_temporary_classes_group.UserApi;
import ru.sargassov.fmweb.dto.player_dtos.*;
import ru.sargassov.fmweb.exceptions.CoachException;
import ru.sargassov.fmweb.intermediate_entities.*;
import ru.sargassov.fmweb.entities.PlayerEntity;
import ru.sargassov.fmweb.exceptions.TeamNotFoundException;
import ru.sargassov.fmweb.intermediate_spi.TeamIntermediateServiceSpi;
import ru.sargassov.fmweb.services.PlayerPriceSetter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class PlayerConverter {
    private final PositionConverter positionConverter;
    private final PlayerPriceSetter playerPriceSetter;
    private final JuniorConverter juniorConverter;
    private final TeamIntermediateServiceSpi teamIntermediateService;


    public Player getIntermediateEntityFromEntity(PlayerEntity playerEntity, User user){
        Player player = new Player();
        var position = positionConverter.getIntermediateEntityFromEnum(playerEntity.getPositionEntity());
        var playerEntityTeamEntityId = playerEntity.getTeamEntity().getId();
        var team = teamIntermediateService.findAll()
                .stream()
                .filter(t -> t.getTeamEntityId() == playerEntityTeamEntityId)
                .findFirst().orElseThrow(() ->
                        new TeamNotFoundException(String.format("Team with id = '%s' not found", playerEntityTeamEntityId)));

        player.setUser(user);
        player.setName(playerEntity.getName());
        player.setNatio(playerEntity.getNatio());
        player.setGkAble(playerEntity.getGkAble());
        player.setDefAble(playerEntity.getDefAble());
        player.setMidAble(playerEntity.getMidAble());
        player.setForwAble(playerEntity.getForwAble());
        player.setCaptainAble(playerEntity.getCaptainAble());
        player.setNumber(playerEntity.getNumber());
        player.setStrategyPlace(playerEntity.getStrategyPlace());
        player.setBirthYear(playerEntity.getBirthYear());
        player.guessTrainigAble();
        player.setPosition(position);
        player.guessPower();
        player.setTeam(team);
        guessPrice(player);
        return player;
    }

    public PlayerSoftSkillDto getPlayerSoftSkillDtoFromIntermediateEntity(Player player) {
        PlayerSoftSkillDto pOnPageDto = new PlayerSoftSkillDto();
        complectSkillsOfPlayerDto(pOnPageDto, player);
        complectSkillsOfPlayerHardSkillDto(pOnPageDto, player);
        complectSkillCreatedPlayerDto(pOnPageDto, player);
        pOnPageDto.setCaptain(booleanToString(player.isCapitan()));
        pOnPageDto.setClub(player.getTeam().getName());
        pOnPageDto.setInjury(player.isInjury());
        pOnPageDto.setStrategyPlace(player.getStrategyPlace() + 1);
        pOnPageDto.setPower(player.getPower());
        pOnPageDto.setTimeBeforeTreat(player.getTimeBeforeTreat());
        pOnPageDto.setTire(player.getTire());
        pOnPageDto.setPrice(player.getPrice());
        pOnPageDto.setNumber(player.getNumber());

        return pOnPageDto;
    }


    public void complectSkillsOfPlayerDto(PlayerDto pDto, Player p){
        pDto.setId(p.getId());
        pDto.setName(p.getName());
    }

    public void complectSkillsOfPlayerHardSkillDto(PlayerHardSkillDto pDto, Player p){
        pDto.setGkAble(p.getGkAble());
        pDto.setDefAble(p.getDefAble());
        pDto.setMidAble(p.getMidAble());
        pDto.setForwAble(p.getForwAble());
        pDto.setPosition(p.getPosition().toString());
        pDto.setNatio(p.getNatio());
        pDto.setCaptainAble(p.getCaptainAble());
        pDto.setBirthYear(p.getBirthYear());
    }

    private void complectSkillCreatedPlayerDto(CreatedPlayerDto pDto, Player p){
        pDto.setTrainingAble(p.getTrainingAble());
        pDto.setNumber(p.getNumber());
    }

    private String booleanToString(boolean capitan) {
        if(capitan) return "Captain";
        return "";
    }

    public PlayerOnPagePlacementsDto getPlayerHardSkillDtoFromIntermediateEntity(Player p) {
        PlayerOnPagePlacementsDto pOnPagePlDto = new PlayerOnPagePlacementsDto();
        complectSkillsOfPlayerDto(pOnPagePlDto, p);
        pOnPagePlDto.setStrategyPlace(p.getStrategyPlace());
        pOnPagePlDto.setNumber(p.getNumber());
        pOnPagePlDto.setPower(p.getPower());
        pOnPagePlDto.setPosition(p.getPosition().toString());
        return pOnPagePlDto;
    }

    private void setSkillsOfIntermediateEntity(Player p, PlayerHardSkillDto playerHardSkillDto){
        p.setGkAble(playerHardSkillDto.getGkAble());
        p.setDefAble(playerHardSkillDto.getDefAble());
        p.setMidAble(playerHardSkillDto.getMidAble());
        p.setForwAble(playerHardSkillDto.getForwAble());
        p.setCaptainAble(playerHardSkillDto.getCaptainAble());
        p.setBirthYear(playerHardSkillDto.getBirthYear());
        p.guessPosition(playerHardSkillDto.getPosition());
        guessPrice(p);
    }

    public Player getIntermediateEntityFromCreatedDto(CreatedPlayerDto createdPlayerDto) {
        Player p = new Player();
        p.setName(createdPlayerDto.getName());
        p.setNatio(createdPlayerDto.getNatio());
        setSkillsOfIntermediateEntity(p, createdPlayerDto);
        p.setStrategyPlace(-100);
        p.guessPower();
        p.setTeam(userApi.getTeam());
        p.guessNumber(createdPlayerDto.getNumber());
        p.guessTrainigAble();
        return p;
    }

    public BigDecimal getPriceOfIntermediateEntityFromCreatedDto(PlayerHardSkillDto playerHardSkillDto) {
        Player p = new Player();
        setSkillsOfIntermediateEntity(p, playerHardSkillDto);
        return BigDecimal.valueOf(playerPriceSetter.createPrice(p)).setScale(2, RoundingMode.HALF_UP);
    }

    public void guessPrice(Player p){
        p.setPrice(BigDecimal.valueOf(
                playerPriceSetter.createPrice(p))
                .setScale(2, RoundingMode.HALF_UP));
    }


    public PlayerOnTrainingDto getPlayerOnTrainingDtoFromPlayer(Player p) {
        List<Coach> coaches = userApi.getTeam().getCoaches();
        List<Player> coachPlayers = coaches.stream()
                .map(Coach::getPlayerOnTraining)
                .collect(Collectors.toList());

        PlayerOnTrainingDto pDto = new PlayerOnTrainingDto();
        complectSkillsOfPlayerDto(pDto, p);
        pDto.setTrainingBalance(p.getTrainingBalance());
        pDto.setPosition(p.getPosition().toString());
        pDto.setTire(p.getTire());

        if(coachPlayers.contains(p)){
            Coach coach = getCoachForCurrentPlayer(coaches, p);
            pDto.setOnTraining(coach.getType().toString() + "/" + coach.getCoachProgram().toString());
            pDto.setTrainingAble(coach.getTrainingAble());
            return pDto;
        }

        pDto.setOnTraining("");
        pDto.setTrainingAble(p.getTrainingAble());
        return pDto;
    }

    private Coach getCoachForCurrentPlayer(List<Coach> coaches, Player p) {
        Optional<Coach> coachOpt = coaches.stream().filter(c -> c.getPlayerOnTraining() == p).findFirst();
        if(coachOpt.isEmpty()){
            log.error("Coach was not found");
            throw new CoachException("Coach was not found");
        }
        return coachOpt.get();
    }

    public IdNamePricePlayerDto getIdNamePricePlayerDtoFromPlayer(Player p) {
        IdNamePricePlayerDto pDto = new IdNamePricePlayerDto();
        complectSkillsOfPlayerDto(pDto, p);
        pDto.setPrice(p.getPrice().divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP));
        return pDto;
    }

    public Player getIntermadiateEntityFromJunior(Junior junior, Position currentPosition, User user) {
        var player = new Player();
        player.setName(junior.getName());
        player.setPosition(currentPosition);
        player.setUser(user);

        return juniorConverter.setSkillForYoungPlayerIntermediateEntity(player);
    }
}
