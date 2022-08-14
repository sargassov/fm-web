package ru.sargassov.fmweb.intermediate_entites.days;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sargassov.fmweb.exceptions.CalendarException;
import ru.sargassov.fmweb.intermediate_entites.Team;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourDay extends Day{

    private int countOfTour;
    private List<Match> matches;

    @Override
    public String toString() {
        return "TourDay{" +
                "matches=" + matches +
                '}';
    }

    public Match getUserTeamMatch(Team userTeam) {
        return matches.stream()
                .filter(m -> m.getHome().equals(userTeam) || m.getAway().equals(userTeam))
                .findFirst()
                .orElseThrow(()
                        -> new CalendarException("Match of " + userTeam.getName() + " not found!"));
    }
}
