package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.Placement;

public interface PlacementIntermediateServiceSpi {
    Placement save(Placement placement);
}
