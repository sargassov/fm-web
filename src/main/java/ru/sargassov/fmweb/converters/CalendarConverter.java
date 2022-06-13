package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.constants.Constant;
import ru.sargassov.fmweb.dto.NameOfMonthDto;
import ru.sargassov.fmweb.dto.days_dtos.MatchDto;
import ru.sargassov.fmweb.dto.days_dtos.EventDto;
import ru.sargassov.fmweb.exceptions.CalendarException;
import ru.sargassov.fmweb.intermediate_entites.Goal;
import ru.sargassov.fmweb.intermediate_entites.Team;
import ru.sargassov.fmweb.intermediate_entites.days.Day;
import ru.sargassov.fmweb.intermediate_entites.days.Match;
import ru.sargassov.fmweb.intermediate_entites.days.TourDay;
import ru.sargassov.fmweb.intermediate_entites.days.TrainDay;
import ru.sargassov.fmweb.services.UserService;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CalendarConverter {
    private final UserService userService;

    public EventDto getEventDtoFromTourDayEntity(TourDay t){
        EventDto eventDto = new EventDto();
        eventDto.setDate(dateFormer(t.getDate()));
        eventDto.setTour(t.getCountOfTour());
        eventDto.setMatches(t.getMatches().stream()
                .map(this::getMatchDtoFromEntity)
                .collect(Collectors.toList()));
        eventDto.setPassed(t.getMatches().get(0).isMatchPassed());
        return eventDto;
    }

    public String dateFormer(LocalDate localDate) {
        return localDate.getDayOfMonth() + " " + localDate.getMonth() + " " + localDate.getYear();
    }

    public MatchDto getMatchDtoFromEntity(Match m){
        MatchDto matchDto = new MatchDto();
        matchDto.setHomeTeam(m.getHome().getName());
        matchDto.setAwayTeam(m.getAway().getName());
        matchDto.setStadium(m.getStadium().getTitle());
        matchDto.setScore(scoreFormer(m));
        matchDto.setGoals(goalsFormer(m));
        return matchDto;
    }

    private String goalsFormer(Match m) {
        String goals = "";
        for(Goal g : m.getScorePlayers())
            goals += g.getScorePlayer() + "(" + g.getMin() + ")";
        return goals;
    }

    private String scoreFormer(Match m) {
        if(!m.isMatchPassed())
            return "";
        return m.getHomeScore() + ":" + m.getAwayScore();
    }

    public EventDto getEventDtoFromDayEntity(Day day) {
        EventDto eventDto = new EventDto();
        eventDto.setDate(dateFormer(day.getDate()));
        eventDto.setType(typeFormer(day));
        eventDto.setEvent(eventFormer(day));
        return eventDto;
    }

    private String eventFormer(Day day) {
        if(day instanceof TrainDay) return "";
        StringBuilder response = new StringBuilder();
        Team userTeam = userService.getUserTeam();
        Match match = getUserMatch(day, userTeam);
        response.append(homeOrAwayInfo(match, userTeam));
        response.append(matchInfo(match));
        if(match.isMatchPassed()) response.append(matchPassedInfo(match));
        return response.toString();
    }

    private String typeFormer(Day day) {
        if(day instanceof TrainDay) return "Train";
        if(day instanceof TourDay) return "Russian Premier League";
        return null;
    }

    private StringBuilder matchPassedInfo(Match match) {
        return new StringBuilder("(")
                .append(match.getHomeScore())
                .append(":")
                .append(match.getAwayScore())
                .append(")");
    }

    private StringBuilder matchInfo(Match match) {
        return new StringBuilder(match.getHome().getName())
                .append(" - ")
                .append(match.getAway().getName())
                .append(" ");
    }

    private StringBuilder homeOrAwayInfo(Match match, Team userTeam) {
        if(match.getHome() == userTeam)
            return new StringBuilder("(H) ");
        else return new StringBuilder("(A) ");
    }

    private Match getUserMatch(Day day, Team userTeam){
       return  ((TourDay) day).getMatches().stream()
                .filter(m -> m.getHome() == userTeam || m.getAway() == userTeam)
                .findFirst()
                .orElseThrow(() ->
                        new CalendarException("Match with userteam not found"));
    }

    public NameOfMonthDto getNameOfMonthDtoFromConstant(String name) {
        NameOfMonthDto dto = new NameOfMonthDto();
        dto.setName(name);
        return dto;
    }

}
