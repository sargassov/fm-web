package ru.sargassov.fmweb.spi.entity;

import ru.sargassov.fmweb.intermediate_entities.Cortage;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

public interface MatrixServiceSpi {

    void createMatrix(User user);
    List<Cortage> getActualMatrix();
}
