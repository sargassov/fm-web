package ru.sargassov.fmweb.dto.days;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourDay extends Day{
    private List<Match> matches;

    @Override
    public String toString() {
        return "TourDay{" +
                "matches=" + matches +
                '}';
    }
}
