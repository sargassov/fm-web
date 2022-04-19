package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.LeagueDto;
import ru.sargassov.fmweb.dto.PlayerDto;
import ru.sargassov.fmweb.entities.Player;
import ru.sargassov.fmweb.exceptions.TeamNotFoundException;

import java.util.Random;

@Component
@AllArgsConstructor
@Slf4j
public class PlayerConverter {
    private final PositionConverter positionConverter;
    private final LeagueDto leagueDto;
    private final PlayerPriceSetter playerPriceSetter;


    public PlayerDto entityToDto(Player player){
        int trainingAbleValue = 20;
        Random random = new Random();

        PlayerDto pDto = new PlayerDto();
        pDto.setId(player.getId());
        pDto.setName(player.getName());
        pDto.setNatio(player.getNatio());
        pDto.setGkAble(player.getGkAble());
        pDto.setDefAble(player.getDefAble());
        pDto.setMidAble(player.getMidAble());
        pDto.setForwAble(player.getForwAble());
        pDto.setCaptainAble(player.getCaptainAble());
        pDto.setNumber(player.getNumber());
        pDto.setStrategyPlace(player.getStrategyPlace());
        pDto.setBirthYear(player.getBirthYear());
        pDto.setTrainingAble(random.nextInt(trainingAbleValue));
        pDto.setPosition(positionConverter.entityToEnum(player.getPosition().getTitle()));
        pDto.setTeam(
                leagueDto.getTeamList().stream()
                .filter(t -> t.getId() == player.getTeam().getId())
                .findFirst().orElseThrow(() ->
                        new TeamNotFoundException(String.format("Team with id = '%s' not found", player.getTeam().getId()))));
        pDto.setPrice(playerPriceSetter.createPrice(pDto));
        return pDto;
    }

}
