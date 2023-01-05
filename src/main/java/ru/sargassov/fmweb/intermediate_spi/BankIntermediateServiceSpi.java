package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.Bank;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

public interface BankIntermediateServiceSpi {
    List<Bank> save(List<Bank> banks);

    Bank save(Bank bank);

    List<Bank> findAllByUser(User user);

    Bank findById(long id);
}
