package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api_temporary_classes_group.BankApi;
import ru.sargassov.fmweb.converters.BankConverter;
import ru.sargassov.fmweb.dto.BankDto;
import ru.sargassov.fmweb.intermediate_entites.Bank;
import ru.sargassov.fmweb.intermediate_entites.Team;
import ru.sargassov.fmweb.repositories.BankRepository;
import ru.sargassov.fmweb.validators.LoanValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class BankService {
    private final BankRepository bankRepository;
    private final BankConverter bankConverter;
    private final BankApi bankApi;
    private final UserService userService;
    private final LoanValidator loanValidator;

    public void loadBanks(){
        log.info("BanksService.getAllBanks");
        bankApi.setBankApiList(bankRepository.findAll().stream()
                .map(bankConverter::getIntermediateEntityFromEntity).collect(Collectors.toList()));
    }

    public List<BankDto> getAllPotencialLoans(Integer parameter) {
        List<Bank> banks = bankApi.getBankApiList();
        return banks.stream()
                .filter(b -> b.getMaxLoanAmount().compareTo(BigDecimal.valueOf(parameter)) > 0)
                .map(b -> bankConverter.getBankDtoFromIntermediateEntity(b, parameter))
                .collect(Collectors.toList());
    }

    public void takeNewLoan(BankDto loan) {
        loanValidator.validate(loan);
        Team team = userService.getUserTeam();
        Bank bank = bankApi.getBankByTitleFromApiList(loan.getTitle());
        bankConverter.getFullLoanInformation(bank, loan);
        bankApi.remove(bank.getTitle());
        team.getLoans().add(bank);
        team.setWealth(team.getWealth().add(bank.getTookMoney()));
    }

    public void returnBankToApi(Bank bank) {
        bankApi.returnBankToApi(bank);
    }
}
