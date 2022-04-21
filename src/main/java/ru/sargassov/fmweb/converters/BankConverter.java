package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.Bank;
import ru.sargassov.fmweb.entities.BankEntity;

@Component
@AllArgsConstructor
public class BankConverter {

    public Bank entityToDto(BankEntity bankEntity){
        Bank bDto = new Bank();
        bDto.setId(bankEntity.getId());
        bDto.setTitle(bankEntity.getTitle());
        bDto.setPercentDay(bankEntity.getPercentDay());
        bDto.setPercentMonth(bankEntity.getPercentMonth());
        bDto.setPercentWeek(bankEntity.getPercentWeek());
        bDto.setFullLoanCoeff(bankEntity.getFullLoanCoeff());
        bDto.setMaxLoanAmount(bankEntity.getMaxLoanAmount());
        return bDto;
    }
}
