package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.intermediate_entities.Position;

import java.util.List;

public interface PositionServiceSpi {

    List<Position> getAllPositions();
}
