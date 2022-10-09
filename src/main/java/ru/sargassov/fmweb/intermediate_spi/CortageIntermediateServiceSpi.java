package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.Cortage;

import java.util.ArrayList;
import java.util.List;

public interface CortageIntermediateServiceSpi {

    Cortage save(Cortage cortage);

    List<Cortage> save(ArrayList<Cortage> cortages);
}
