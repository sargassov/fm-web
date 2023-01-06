package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.dto.LoanDto;
import ru.sargassov.fmweb.dto.text_responses.InformationDto;
import ru.sargassov.fmweb.dto.text_responses.TextResponse;

import java.util.List;

public interface FinanceServiceSpi {
    
    List<InformationDto> gelAllIncomes();

    List<InformationDto> gelAllExpenses();

    List<LoanDto> loadCurrentLoans();
    
    TextResponse getStartFinanceMessage();

    void remainCurrentLoan(LoanDto loan);
}
