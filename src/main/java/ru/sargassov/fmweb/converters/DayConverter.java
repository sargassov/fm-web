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
        day.setMatch(dayEntity.isMatch());
        day.setPresent(dayEntity.isPresent());

        return day;
    }

//    public List<Match> descriptionOfTourToMatches(List<String> descriptionOfTour) {
//        List<Match> matches = new ArrayList<>();
//        String deliver = "-";
//
//        for(String s : descriptionOfTour){
//            String[] homeGuest = s.split(deliver);
//
//            Match m = new Match();
//            m.setHome(teamApi.findByName(homeGuest[0]));
//            m.setAway(teamApi.findByName(homeGuest[1]));
//            m.setStadium(m.getHome().getStadium());
//            m.setScorePlayers(new ArrayList<>());
//            matches.add(m);
//        }
//
//        return matches;
//    }

    public DayDto dtoToPresentDayRequest(Day presentDay) {
        DayDto dayDto = new DayDto();
        dayDto.setDay(presentDay.getDate().getDayOfMonth());
        dayDto.setMonth(presentDay.getDate().getMonth().toString());
        dayDto.setYear(presentDay.getDate().getYear());
        log.info(dayDto.toString());
        return dayDto;
    }
}
