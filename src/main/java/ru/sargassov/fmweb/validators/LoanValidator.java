package ru.sargassov.fmweb.validators;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.BankDto;
import ru.sargassov.fmweb.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class LoanValidator {
    private List<String> errors = new ArrayList<>();

    public void validate(BankDto bankDto) {
        List<String> errors = new ArrayList<>();

        if (bankDto.getTookMoney().compareTo(bankDto.getMaxLoanAmount()) > 0) {
            errors.add("Full loan amount is critical high");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
