package ru.sargassov.fmweb.intermediate_entites;

import lombok.Data;
import lombok.Getter;
import ru.sargassov.fmweb.intermediate_entites.days.Match;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cortage {
    private Team team;
    private List<Match> matchList;

    public List<Match> getMatchList() {
        if (matchList == null) {
            matchList = new ArrayList<>();
        }
        return matchList;
    }
}
