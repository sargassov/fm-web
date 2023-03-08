package ru.sargassov.fmweb.spi.intermediate_spi;

import ru.sargassov.fmweb.dto.FinalPayment;
import ru.sargassov.fmweb.intermediate_entities.Bank;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

public interface BankIntermediateServiceSpi {
    List<Bank> save(List<Bank> banks);

    Bank save(Bank bank);

    List<Bank> findAllByUser(User user);

    Bank findById(long id);

    List<String> paymentPeriod(Bank bank, Bank.TypeOfReturn type, Team team, FinalPayment finalPayment);

    List<Bank> findByTeam(Team team);
}
