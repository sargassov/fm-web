package ru.sargassov.fmweb.spi.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.Draw;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.ArrayList;
import java.util.List;

public interface DrawIntermediateServiceSpi {
    Draw save(Draw draw);

    List<Draw> findAllByUser(User user);

    List<Draw> save(ArrayList<Draw> drawList);
}
