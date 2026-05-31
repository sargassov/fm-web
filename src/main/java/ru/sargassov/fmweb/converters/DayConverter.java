package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.days_dtos.DayDto;
import ru.sargassov.fmweb.intermediate_entities.Day;
import ru.sargassov.fmweb.entities.DayEntity;
import ru.sargassov.fmweb.intermediate_entities.User;


@Component
@AllArgsConstructor
@Slf4j
public class DayConverter {

    public Day getIntermediateEntityFromEntity(DayEntity dayEntity, User user){
        var day = new Day();
        day.setUser(user);
        day.setDate(dayEntity.getDate());
        day.setPassed(dayEntity.isPassed());
        day.setLeagueDay(dayEntity.isLeagueDay());
        day.setCupDay(dayEntity.isCupDay());
        day.setPresent(dayEntity.isPresent());

        return day;
    }

    public DayDto dtoToPresentDayRequest(Day presentDay) {
        DayDto dayDto = new DayDto();
        dayDto.setDay(presentDay.getDate().getDayOfMonth());
        dayDto.setMonth(presentDay.getDate().getMonth().toString());
        dayDto.setYear(presentDay.getDate().getYear());
        log.info(dayDto.toString());
        return dayDto;
    }
}
