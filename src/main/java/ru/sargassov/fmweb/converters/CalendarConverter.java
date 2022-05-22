package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.days_dtos.MatchDto;
import ru.sargassov.fmweb.dto.days_dtos.TourDto;
import ru.sargassov.fmweb.intermediate_entites.Goal;
import ru.sargassov.fmweb.intermediate_entites.days.Match;
import ru.sargassov.fmweb.intermediate_entites.days.TourDay;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CalendarConverter {

    public TourDto getTourDtoFromTourDayEntity(TourDay t){
        TourDto tourDto = new TourDto();
        tourDto.setDate(dateFormer(t.getDate()));
        tourDto.setTour(t.getCountOfTour());
        tourDto.setMatches(t.getMatches().stream()
                .map(this::getMatchDtoFromEntity)
                .collect(Collectors.toList()));
        tourDto.setPassed(t.getMatches().get(0).isMatchPassed());
        return tourDto;
    }

    private String dateFormer(LocalDate localDate) {
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
}
