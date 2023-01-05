package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.converters.BankConverter;
import ru.sargassov.fmweb.dto.BankDto;
import ru.sargassov.fmweb.dto.LoanDto;
import ru.sargassov.fmweb.exceptions.TooExpensiveException;
import ru.sargassov.fmweb.intermediate_entities.Bank;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.entity_repositories.BankRepository;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_spi.BankIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.BankServiceSpi;
import ru.sargassov.fmweb.validators.LoanValidator;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class BankService implements BankServiceSpi {
    private final BankRepository bankRepository;
    private final BankConverter bankConverter;
    private final BankIntermediateServiceSpi bankIntermediateService;
    private final LoanValidator loanValidator;

    @Transactional
    @Override
    public void loadBanks(User user){
        log.info("BanksService.loadBanks");
        var bankEntites = bankRepository.findAll();
        var banks = bankEntites
                .stream()
                .map(be -> bankConverter.getIntermediateEntityFromEntity(be, user))
                .collect(Collectors.toList());
        bankIntermediateService.save(banks);
    }

    @Transactional
    @Override
    public List<BankDto> getAllPotencialLoans(Integer parameter) {
        var banks = bankIntermediateService.findAllByUser(UserHolder.user);
        return banks.stream()
                .filter(b -> b.getMaxLoanAmount().compareTo(BigDecimal.valueOf(parameter)) > 0)
                .filter(Bank::isActive)
                .map(b -> bankConverter.getBankDtoFromIntermediateEntity(b, parameter))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void remainCurrentLoan(LoanDto loan) {
        var userteam = UserHolder.user.getUserTeam();

        if (userteam.getWealth().compareTo(loan.getRemainsToPay()) < 0){
            throw new TooExpensiveException(userteam.getName() + " haven't enough money (" + loan.getRemainsToPay() + ") to remains this loan!");
        }

        userteam.deleteBankById(loan.getId());
        userteam.setWealth(userteam.getWealth().subtract(loan.getRemainsToPay()));
        var banks = bankIntermediateService.findAllByUser(UserHolder.user);
        var bank = bankIntermediateService.findById(loan.getId());
        bank.remainLoan();
        userteam.getLoans().remove(bank);
    }

    @Transactional
    @Override
    public void takeNewLoan(BankDto loan) {
        loanValidator.validate(loan);
        var userTeam = UserHolder.user.getUserTeam();
        var bank = bankIntermediateService.findById(loan.getId());
        bankConverter.getFullLoanInformation(bank, loan);
        bank.setActive(false);
        userTeam.getLoans().add(bank);
        userTeam.setWealth(userTeam.getWealth().add(bank.getTookMoney()));
    }

    @Override
    @Transactional
    public List<LoanDto> getLoanDtoFromIntermediateEntity(Team userTeam) {
        return userTeam.getLoans().stream()
                .map(bankConverter::getLoanDtoFromIntermediateEntity)
                .collect(Collectors.toList());
    }
}
