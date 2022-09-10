package ru.sargassov.fmweb.intermediate_entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import ru.sargassov.fmweb.constants.BaseUserEntity;
import ru.sargassov.fmweb.exceptions.CalendarException;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "day")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Day extends BaseUserEntity {

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "is_passed")
    private boolean isPassed;

    @Column(name = "is_present")
    private boolean isPresent;

    @Column(name = "is_match")
    private boolean isMatch;

    @Column(name = "count_of_tour")
    private Integer countOfTour;

    @OneToMany(mappedBy = "tourDay")
    private List<Match> matches;

    @Type(type = "string-array")
    @Column(name = "changes", columnDefinition = "text[]")
    private String[] noteOfChanges;


    public Match getUserTeamMatch(Team userTeam) {
        return matches.stream()
                .filter(m -> m.getHome().equals(userTeam) || m.getAway().equals(userTeam))
                .findFirst()
                .orElseThrow(()
                        -> new CalendarException("Match of " + userTeam.getName() + " not found!"));
    }
    public List<String> getNoteOfChanges() {
        if (noteOfChanges == null) {
            return new ArrayList<>();
        }
        return List.of(noteOfChanges);
    }
}
