package ru.sargassov.fmweb.intermediate_entites.days;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Day {
    private Long id;
    private LocalDate date;
    private boolean isPassed;
    private boolean isPresent;
    private boolean isMatch;
}
