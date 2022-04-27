package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.api.TeamApi;
import ru.sargassov.fmweb.dto.League;
import ru.sargassov.fmweb.dto.Player;
import ru.sargassov.fmweb.dto.Stadium;
import ru.sargassov.fmweb.dto.Team;
import ru.sargassov.fmweb.dto.days.*;
import ru.sargassov.fmweb.entities.DayEntity;
import ru.sargassov.fmweb.exceptions.PresentDayNotFoundException;
import ru.sargassov.fmweb.exceptions.TeamNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class DayConverter {
    private final TeamApi teamApi;

    public Day entityToDto(DayEntity dayEntity){
        Day day;

        if (dayEntity.isMatch()) {
            day = new TourDay();
            day.setMatch(true);
        }
        else day = new TrainDay();

        day.setDate(dayEntity.getDate());
        day.setId(dayEntity.getId());
        day.setPassed(dayEntity.isPassed());
        day.setMatch(dayEntity.isMatch());
        day.setPresent(dayEntity.isPresent());

        return day;
    }

    public List<Match> descriptionOfTourToMatches(List<String> descriptionOfTour) {
        List<Match> matches = new ArrayList<>();
        String deliver = "-";

        for(String s : descriptionOfTour){
            String[] homeGuest = s.split(deliver);

            Match m = new Match();
            m.setHome(teamApi.findByName(homeGuest[0]));
            m.setAway(teamApi.findByName(homeGuest[1]));
            m.setStadium(m.getHome().getStadium());
            m.setScorePlayers(new ArrayList<>());
            matches.add(m);
        }

        return matches;
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
