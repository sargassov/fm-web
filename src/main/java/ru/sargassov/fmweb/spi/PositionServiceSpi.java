package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.intermediate_entites.Position;

import java.util.List;

public interface PositionServiceSpi {

    List<Position> getAllPositions();
}
