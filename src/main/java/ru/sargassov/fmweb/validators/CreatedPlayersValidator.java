package ru.sargassov.fmweb.validators;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.player_dtos.CreatedPlayerDto;
import ru.sargassov.fmweb.exceptions.ValidationException;
import ru.sargassov.fmweb.intermediate_entites.Player;
import ru.sargassov.fmweb.intermediate_entites.Team;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class CreatedPlayersValidator {
    private List<String> errors = new ArrayList<>();

    public void newPlayervValidate(CreatedPlayerDto createdPlayerDto) {
        List<String> errors = new ArrayList<>();

        if (createdPlayerDto.getName().isBlank()) {
            errors.add("Name of new Player can't be blank!");
        }
        if (createdPlayerDto.getNatio().isBlank()) {
            errors.add("Natio of new Player can't be blank!");
        }
        if (createdPlayerDto.getPosition().isBlank()) {
            errors.add("Position of new Player can't be blank!");
        }
        if (createdPlayerDto.getGkAble() < 1 || createdPlayerDto.getGkAble() > 99) {
            errors.add("Goalkeeper's abilities can't be less than 1 and more than 99!");
        }
        if (createdPlayerDto.getDefAble() < 1 || createdPlayerDto.getDefAble() > 99) {
            errors.add("Defender's abilities can't be less than 1 and more than 99!");
        }
        if (createdPlayerDto.getMidAble() < 1 || createdPlayerDto.getMidAble() > 99) {
            errors.add("Midfielder's abilities can't be less than 1 and more than 99!");
        }
        if (createdPlayerDto.getForwAble() < 1 || createdPlayerDto.getForwAble() > 99) {
            errors.add("Forwards's abilities can't be less than 1 and more than 99!");
        }
        if (createdPlayerDto.getTrainingAble() < 10 || createdPlayerDto.getTrainingAble() > 19) {
            errors.add("Training abilities can't be less than 10 and more than 19!");
        }
        if (createdPlayerDto.getBirthYear() < 1980 || createdPlayerDto.getBirthYear() > 2004) {
            errors.add("Forwards's abilities can't be less than 1980 and more than 2004!");
        }
        if (createdPlayerDto.getNumber() < 1 || createdPlayerDto.getNumber() > 99) {
            errors.add("Team's number abilities can't be less than 1 and more than 99!");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    public void teamEnoughCreditsValidate(Player player, Team team) {
        List<String> errors = new ArrayList<>();

        if (player.getPrice().compareTo(team.getWealth()) > 0) {
            errors.add("New Player too expensive for you club");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
