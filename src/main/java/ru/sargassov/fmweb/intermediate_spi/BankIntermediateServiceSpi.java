package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.Bank;

import java.util.List;

public interface BankIntermediateServiceSpi {
    void save(List<Bank> banks);
}
