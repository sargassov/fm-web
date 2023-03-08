package ru.sargassov.fmweb.spi.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.Cortage;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.ArrayList;
import java.util.List;

public interface CortageIntermediateServiceSpi {

    Cortage save(Cortage cortage);

    List<Cortage> save(ArrayList<Cortage> cortages);

    List<Cortage> getByUser(User user);
}
