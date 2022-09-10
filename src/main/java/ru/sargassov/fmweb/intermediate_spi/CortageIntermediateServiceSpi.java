package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.Cortage;

import java.util.List;

public interface CortageIntermediateServiceSpi {
    List<Cortage> constructMatrix();

    Cortage save(Cortage cortage);
}
