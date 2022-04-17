package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.BankDto;
import ru.sargassov.fmweb.dto.LeagueDto;
import ru.sargassov.fmweb.entities.Bank;

@Component
@AllArgsConstructor
public class BankConverter {

    public BankDto entityToDto(Bank bank){
        BankDto bDto = new BankDto();
        bDto.setId(bank.getId());
        bDto.setTitle(bank.getTitle());
        bDto.setPercentDay(bank.getPercentDay());
        bDto.setPercentMonth(bank.getPercentMonth());
        bDto.setPercentWeek(bank.getPercentWeek());
        bDto.setFullLoanCoeff(bank.getFullLoanCoeff());
        bDto.setMaxLoanAmount(bank.getMaxLoanAmount());
        return bDto;
    }
}
