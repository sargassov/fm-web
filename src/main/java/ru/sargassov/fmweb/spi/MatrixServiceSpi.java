package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.intermediate_entites.Cortage;

import java.util.List;

public interface MatrixServiceSpi {
    List<Cortage> getActualMatrix();
}
