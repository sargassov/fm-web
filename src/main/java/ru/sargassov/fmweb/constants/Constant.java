package ru.sargassov.fmweb.constants;

import org.hibernate.annotations.BatchSize;

public final class Constant {
    public static int placementSize = 18;
    public enum Month {
        AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER, JANUARY,
        FEBRUARY, MARCH, APRIL, MAY, JUNE
    }

    public static final int DEFAULT_BATCH_SIZE = 100;

    public static int MAX_TIRE_FOR_TRAIN = 50;
}
