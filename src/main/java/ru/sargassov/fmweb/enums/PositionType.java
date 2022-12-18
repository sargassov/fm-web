package ru.sargassov.fmweb.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PositionType {
    GOALKEEPER("Goalkeeper"),
    DEFENDER("Defender"),
    MIDFIELDER("Midfielder"),
    FORWARD("Forward");

    private final String description;

    public static PositionType findByDescription(String description) {
        var positions = PositionType.values();
        for (var pt : positions) {
            if (pt.getDescription().equalsIgnoreCase(description)) {
                return pt;
            }
        }
        throw new IllegalStateException("Unexpected position");
    }
}
