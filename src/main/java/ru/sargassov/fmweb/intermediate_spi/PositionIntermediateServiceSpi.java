package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Position;

import java.util.List;
import java.util.Optional;

public interface PositionIntermediateServiceSpi {
    Optional<Position> findByTitle(String title);

    Position save(Position newPosition);

    List<Position> findAll();
}
