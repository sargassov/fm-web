package ru.sargassov.fmweb.dto.days;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class DayDto {
    private int day;
    private String month;
    private int year;

    @Override
    public String toString() {
        return "DayDto{" +
                "day=" + day +
                ", month='" + month + '\'' +
                ", year=" + year +
                '}';
    }
}
