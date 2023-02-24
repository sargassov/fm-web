package ru.sargassov.fmweb.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.constants.FinanceAnalytics;
import ru.sargassov.fmweb.constants.TextConstant;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.dto.LoanDto;
import ru.sargassov.fmweb.dto.text_responses.InformationDto;
import ru.sargassov.fmweb.dto.text_responses.TextResponse;
import ru.sargassov.fmweb.intermediate_spi.TeamIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.BankServiceSpi;
import ru.sargassov.fmweb.spi.FinanceServiceSpi;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FinanceService implements FinanceServiceSpi {

    private final BankServiceSpi bankService;
    private final TeamIntermediateServiceSpi teamIntermediateService;

    @Override
    public List<InformationDto> gelAllIncomes() {
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        return FinanceAnalytics.getIncomes(userTeam);
    }

    @Override
    public List<InformationDto> gelAllExpenses() {
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        return FinanceAnalytics.getExpenses(userTeam);
    }

    @Override
    public List<LoanDto> loadCurrentLoans() {
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        return bankService.getLoanDtoFromIntermediateEntity(userTeam);
    }

    @Override
    public TextResponse getStartFinanceMessage() {
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
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
