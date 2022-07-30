package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.dto.BankDto;
import ru.sargassov.fmweb.dto.LoanDto;
import ru.sargassov.fmweb.intermediate_entites.Bank;
import ru.sargassov.fmweb.intermediate_entites.Team;

import java.util.List;

public interface BankServiceSpi {

    void loadBanks();

    List<BankDto> getAllPotencialLoans(Integer parameter);

    void takeNewLoan(BankDto loan);

    void returnBankToApi(Bank bank);

    List<LoanDto> getLoanDtoFromIntermediateEntity(Team userTeam);
}
