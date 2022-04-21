package ru.sargassov.fmweb.dto.days;

import lombok.Data;

@Data
public class TrainDay extends Day {
    @Override
    public String toString() {
        return "TrainDay{}";
    }
}
