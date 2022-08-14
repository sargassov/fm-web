package ru.sargassov.fmweb.intermediate_entites.days;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class Day {
    private Long id;
    private LocalDate date;
    private boolean isPassed;
    private boolean isPresent;
    private boolean isMatch;
    private List<String> noteOfChanges;

    public List<String> getNoteOfChanges() {
        if (noteOfChanges == null) {
            return new ArrayList<>();
        }
        return noteOfChanges;
    }
}
