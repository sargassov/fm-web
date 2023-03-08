package ru.sargassov.fmweb.spi.entity;

import ru.sargassov.fmweb.dto.BankDto;
import ru.sargassov.fmweb.dto.LoanDto;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

public interface BankServiceSpi {

    void loadBanks(User user);

    List<BankDto> getAllPotencialLoans(Integer parameter);
//
    void takeNewLoan(BankDto loan);
//
//    void returnBankToApi(Bank bank);

    void remainCurrentLoan(LoanDto loan);

    List<LoanDto> getLoanDtoFromIntermediateEntity(Team userTeam);
}
