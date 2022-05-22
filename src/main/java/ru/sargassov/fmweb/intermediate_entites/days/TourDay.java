package ru.sargassov.fmweb.intermediate_entites.days;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
