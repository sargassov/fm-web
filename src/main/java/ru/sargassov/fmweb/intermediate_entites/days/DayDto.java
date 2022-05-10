package ru.sargassov.fmweb.intermediate_entites.days;

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
