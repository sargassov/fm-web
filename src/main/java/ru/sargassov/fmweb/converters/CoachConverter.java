package ru.sargassov.fmweb.converters;

import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.dto.CoachDto;
import ru.sargassov.fmweb.enums.PositionType;
import ru.sargassov.fmweb.intermediate_entities.Coach;

import static ru.sargassov.fmweb.intermediate_entities.Coach.CoachProgram.STANDART;

@Component
public class CoachConverter {

    public CoachDto getCoachDtoFromIntermediateEntity(Coach coach){
        var cDto = new CoachDto();
        cDto.setId(coach.getId());
        cDto.setPosition(coach.getPosition().toString());
        cDto.setType(coach.getType().toString());
        cDto.setCoachProgram(coach.getCoachProgram().toString());
        if(coach.getPlayerOnTraining() == null)
            cDto.setPlayerOnTraining("N/N");
        else{
            cDto.setPlayerOnTraining(coach.getPlayerOnTraining().getName());
            cDto.setPlayerTrAble(coach.getTrainingAble());
        }

        return cDto;
    }


    public Coach getIntermediateEntityFromCoachDto(CoachDto coachDto) {
        var coach = new Coach();
        var user = UserHolder.user;
        var coachPosition = PositionType.defineByDescription(coachDto.getPosition());
        coach.setPosition(coachPosition);
        coach.setType(coachDto.guessType());
        coach.setPrice(coachDto.guessPrice());
        coach.setCoachProgram(STANDART);
        coach.setUser(user);
        return coach;
    }
}
