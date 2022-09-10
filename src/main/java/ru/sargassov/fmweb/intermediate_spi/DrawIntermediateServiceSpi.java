package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.Draw;

import java.util.List;

public interface DrawIntermediateServiceSpi {
    Draw save(Draw draw);

    List<Draw> findAll();
}
