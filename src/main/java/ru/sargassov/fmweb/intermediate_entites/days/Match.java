package ru.sargassov.fmweb.intermediate_entites.days;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.intermediate_entites.Goal;
import ru.sargassov.fmweb.intermediate_entites.Player;
import ru.sargassov.fmweb.intermediate_entites.Stadium;
import ru.sargassov.fmweb.intermediate_entites.Team;
import ru.sargassov.fmweb.spi.TeamServiceSpi;

import java.util.List;

@Data
public class Match {

    private Team home;
    private Team away;
    private Stadium stadium;

    private boolean impossibleMatch;

    private List<Goal> scorePlayers;

    private int homeScore;
    private int awayScore;
    private boolean matchPassed;

    public Match() {}

    public Match(boolean impossible) {
        impossibleMatch = impossible;
    }

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


}
