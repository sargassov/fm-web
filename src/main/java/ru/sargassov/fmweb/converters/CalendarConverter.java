package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.dto.NameOfMonthDto;
import ru.sargassov.fmweb.dto.days_dtos.EventDto;
import ru.sargassov.fmweb.dto.days_dtos.MatchDto;
import ru.sargassov.fmweb.exceptions.CalendarException;
import ru.sargassov.fmweb.intermediate_entities.Day;
import ru.sargassov.fmweb.intermediate_entities.Goal;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.Match;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CalendarConverter {

    private static final String PASSED = "Passed";
    private static final String EMPTY = "";

    public EventDto getEventDtoFromTourDayEntity(Day matchDay){
        var eventDto = new EventDto();
        eventDto.setDate(dateFormer(matchDay.getDate()));
        eventDto.setTour(matchDay.getCountOfTour());
        eventDto.setMatches(matchDay.getMatches().stream()
                .map(this::getMatchDtoFromEntity)
                .collect(Collectors.toList()));

        String passed = matchDay.getMatches().get(0).isMatchPassed()
                ? PASSED
                : EMPTY;
        eventDto.setPassed(passed);
        return eventDto;
    }

    public String dateFormer(LocalDate localDate) {
        return localDate.getDayOfMonth() + " " + localDate.getMonth() + " " + localDate.getYear();
    }

    public MatchDto getMatchDtoFromEntity(Match m){
        var matchDto = new MatchDto();
        matchDto.setHomeTeam(m.getHome().getName());
        matchDto.setAwayTeam(m.getAway().getName());
        matchDto.setStadium(m.getStadium().getTitle());
        matchDto.setScore(scoreFormer(m));
        matchDto.setGoals(goalsFormer(m));
        return matchDto;
    }

    private String goalsFormer(Match m) {
        var allGoals = new ArrayList<>(m.getGoals());
        var allGoalsSorted = allGoals
                .stream()
                .sorted(Comparator.comparing(Goal::getMin))
                .collect(Collectors.toList());
        StringBuilder goals = new StringBuilder(EMPTY);
        for(var g : allGoalsSorted) {
            goals.append(g.getScorePlayer().getName()).append("(").append(g.getMin()).append("), ");
        }
        var stringGoals = goals.toString();
        if (stringGoals.isEmpty()) {
            return stringGoals;
        }
        return goals.substring(0, goals.length() - 2);
    }

    private String scoreFormer(Match m) {
        if(!m.isMatchPassed())
            return "";
        return m.getHomeScore() + ":" + m.getAwayScore();
    }

    public EventDto getEventDtoFromDayEntity(Day day) {
        var eventDto = new EventDto();
        eventDto.setDate(dateFormer(day.getDate()));
        eventDto.setType(typeFormer(day));
        eventDto.setEvent(eventFormer(day));

        String passed = day.isPassed()
                ? PASSED
                : EMPTY;
        eventDto.setPassed(passed);

        return eventDto;
    }

    private String eventFormer(Day day) {
        if (!day.isMatch()) {
            return "";
        }
        var response = new StringBuilder();
        var userTeam = UserHolder.user.getUserTeam();
        var match = getUserMatch(day, userTeam);
        response.append(homeOrAwayInfo(match, userTeam));
        response.append(matchInfo(match));
        if(match.isMatchPassed()) response.append(matchPassedInfo(match));
        return response.toString();
    }

    private String typeFormer(Day day) {
        if (day.isMatch()) {
            return "Russian Premier League";
        }
        return "Train";
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
       return  (day.getMatches().stream()
                .filter(m -> m.getHome().equals(userTeam) || m.getAway().equals(userTeam))
                .findFirst()
                .orElseThrow(() ->
                        new CalendarException("Match with userteam not found")));
    }

    public NameOfMonthDto getNameOfMonthDtoFromConstant(String name) {
        NameOfMonthDto dto = new NameOfMonthDto();
        dto.setName(name);
        return dto;
    }

}
