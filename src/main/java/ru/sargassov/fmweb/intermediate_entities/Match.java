package ru.sargassov.fmweb.intermediate_entities;

import lombok.*;
import ru.sargassov.fmweb.constants.BaseUserEntity;
import ru.sargassov.fmweb.intermediate_entities.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "match")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Match extends BaseUserEntity {

    @OneToOne
    @JoinColumn(name = "id_home_team")
    private Team home;

    @OneToOne
    @JoinColumn(name = "id_away_team")
    private Team away;

    @OneToOne
    @JoinColumn(name = "id_stadium")
    private Stadium stadium;

    @ManyToOne
    @JoinColumn(name = "id_tour_day")
    private Day tourDay;

    @ManyToOne
    @JoinColumn(name = "id_cortage")
    private Cortage cortage;

    @Column(name = "impossible_match")
    private boolean impossibleMatch;

    @OneToMany(mappedBy = "match")
    private List<Goal> goals;

    @Column(name = "home_score")
    private int homeScore;

    @Column(name = "away_score")
    private int awayScore;

    @Column(name = "match_passed")
    private boolean matchPassed;

    public Match(boolean impossible) {
        impossibleMatch = impossible;
    }

    @Override
    public String toString() {
        return "MatchDay{" +
                "home=" + home +
                ", away=" + away +
                ", stadium=" + stadium +
                ", scorePlayers=" + goals +
                ", homeScore=" + homeScore +
                ", awayScore=" + awayScore +
                ", matchPassed=" + matchPassed +
                '}';
    }


}
