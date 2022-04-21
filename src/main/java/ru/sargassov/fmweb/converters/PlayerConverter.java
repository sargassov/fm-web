package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.api.TeamApi;
import ru.sargassov.fmweb.dto.League;
import ru.sargassov.fmweb.dto.Player;
import ru.sargassov.fmweb.entities.PlayerEntity;
import ru.sargassov.fmweb.exceptions.TeamNotFoundException;
import ru.sargassov.fmweb.services.TeamService;

import java.util.Random;

@Component
@AllArgsConstructor
@Slf4j
public class PlayerConverter {
    private final PositionConverter positionConverter;
    private final PlayerPriceSetter playerPriceSetter;
    private final TeamApi teamApi;


    public Player entityToDto(PlayerEntity playerEntity){
        int trainingAbleValue = 20;
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
        pDto.setTrainingAble(random.nextInt(trainingAbleValue));
        pDto.setPosition(positionConverter.entityToEnum(playerEntity.getPositionEntity().getTitle()));
        pDto.setTeam(teamApi.getTeamApiList()
                .stream()
                .filter(t -> t.getId() == playerEntity.getTeamEntity().getId())
                .findFirst().orElseThrow(() ->
                        new TeamNotFoundException(String.format("Team with id = '%s' not found", playerEntity.getTeamEntity().getId()))));
        pDto.setPrice(playerPriceSetter.createPrice(pDto));
        return pDto;
    }

}
