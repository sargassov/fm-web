package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.Day;
import ru.sargassov.fmweb.intermediate_entities.Team;

import java.util.List;

public interface DayIntermediateServiceSpi {
    Day save(Day d);
    List<Day> findAllWhereIsMatchTrue();

