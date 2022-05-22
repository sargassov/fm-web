package ru.sargassov.fmweb.intermediate_entites.days;

import lombok.Data;
import ru.sargassov.fmweb.intermediate_entites.Goal;
import ru.sargassov.fmweb.intermediate_entites.Player;
import ru.sargassov.fmweb.intermediate_entites.Stadium;
import ru.sargassov.fmweb.intermediate_entites.Team;

import java.util.List;

@Data
public class Match {

    private Team home;
    private Team away;
    private Stadium stadium;

    private List<Goal> scorePlayers;

    private int homeScore;
    private int awayScore;
    private boolean matchPassed;

    @Override
    public String toString() {
        return "MatchDay{" +
                "home=" + home +
                ", away=" + away +
                ", stadium=" + stadium +
                ", scorePlayers=" + scorePlayers +
                ", homeScore=" + homeScore +
                ", awayScore=" + awayScore +
                ", matchPassed=" + matchPassed +
                '}';
    }

    //    @Override
//    public DayMatch clone() {
//        DayMatch dayMatch = null;
//        return (DayMatch) super.clone();
//    }
}
