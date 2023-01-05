package ru.sargassov.fmweb.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.constants.FinanceAnalytics;
import ru.sargassov.fmweb.constants.TextConstant;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.dto.LoanDto;
import ru.sargassov.fmweb.dto.text_responses.InformationDto;
import ru.sargassov.fmweb.dto.text_responses.TextResponse;
import ru.sargassov.fmweb.spi.BankServiceSpi;
import ru.sargassov.fmweb.spi.FinanceServiceSpi;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FinanceService implements FinanceServiceSpi {

    private final BankServiceSpi bankService;

    @Override
    public List<InformationDto> gelAllIncomes() {
        var userTeam = UserHolder.user.getUserTeam();
        return FinanceAnalytics.getIncomes(userTeam);
    }

    @Override
    public List<InformationDto> gelAllExpenses() {
        var team = UserHolder.user.getUserTeam();
        return FinanceAnalytics.getExpenses(team);
    }

    @Transactional
    @Override
    public List<LoanDto> loadCurrentLoans() {
        var userTeam = UserHolder.user.getUserTeam();
        return bankService.getLoanDtoFromIntermediateEntity(userTeam);
    }

    @Override
    public TextResponse getStartFinanceMessage() {
        var userTeam = UserHolder.user.getUserTeam();
        var banksValue = userTeam.getLoans().size();
        var text = new TextResponse();
        text.setResponse(TextConstant.getBanksStartMessage(userTeam, banksValue));
        if (text.getResponse().endsWith("more!")) {
            text.setCondition(false);
        } else {
            text.setCondition(true);
        }
        return text;
    }

    @Override
    public void remainCurrentLoan(LoanDto loan) {
        bankService.remainCurrentLoan(loan);
    }

}
