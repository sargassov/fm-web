package ru.sargassov.fmweb.converters;

import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.CoachDto;
import ru.sargassov.fmweb.intermediate_entities.Coach;

@Component
public class CoachConverter {

    public CoachDto getCoachDtoFromIntermediateEntity(Coach coach){
        CoachDto cDto = new CoachDto();
        cDto.setCount(coach.getCount());
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
        Coach coach = new Coach();
//        coach.setPosition(coachDto.guessPosition());
        coach.setType(coachDto.guessType());
        coach.setPrice(coachDto.guessPrice());
        coach.setCoachProgram(Coach.CoachProgram.STANDART);
        return coach;
    }
}
