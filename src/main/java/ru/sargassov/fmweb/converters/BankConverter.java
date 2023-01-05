package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.BankDto;
import ru.sargassov.fmweb.dto.LoanDto;
import ru.sargassov.fmweb.intermediate_entities.Bank;
import ru.sargassov.fmweb.entities.BankEntity;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_spi.DayIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.CalendarServiceSpi;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@AllArgsConstructor
public class BankConverter {
    private final DayIntermediateServiceSpi dayIntermediateService;
    private final CalendarConverter calendarConverter;

    public Bank getIntermediateEntityFromEntity(BankEntity bankEntity, User user){
        Bank bank = new Bank();
        bank.setUser(user);
        bank.setTitle(bankEntity.getTitle());
        bank.setPercentDay(bankEntity.getPercentDay());
        bank.setPercentMonth(bankEntity.getPercentMonth());
        bank.setPercentWeek(bankEntity.getPercentWeek());
        bank.setFullLoanCoeff(bankEntity.getFullLoanCoeff());
        bank.setMaxLoanAmount(BigDecimal.valueOf(bankEntity.getMaxLoanAmount()));
        return bank;
    }

    public BankDto getBankDtoFromIntermediateEntity(Bank b, int parameter) {
        BigDecimal newLoanChecking = BigDecimal.valueOf(parameter * b.getFullLoanCoeff());

        BankDto bankDto = new BankDto();
        bankDto.setTitle(b.getTitle());
        bankDto.setPercentDay(getPercent(newLoanChecking, b.getPercentDay())
                .setScale(2, RoundingMode.HALF_UP));
        bankDto.setPercentWeek(getPercent(newLoanChecking, b.getPercentWeek())
                .setScale(2, RoundingMode.HALF_UP));
        bankDto.setPercentMonth(getPercent(newLoanChecking, b.getPercentMonth())
                .setScale(2, RoundingMode.HALF_UP));
        bankDto.setFullLoanCoeff(BigDecimal.valueOf(b.getFullLoanCoeff())
                .multiply(BigDecimal.valueOf(parameter))
                .setScale(2, RoundingMode.HALF_UP));
        bankDto.setMaxLoanAmount(b.getMaxLoanAmount().setScale(2, RoundingMode.HALF_UP));
        bankDto.setTookMoney(BigDecimal.valueOf(parameter).setScale(2, RoundingMode.HALF_UP));
        bankDto.setId(b.getId());
        return bankDto;
    }

    private BigDecimal getPercent(BigDecimal value, int percent){
        return value.divide(BigDecimal.valueOf(100))
                .multiply(BigDecimal.valueOf(percent));
    }

    public void getFullLoanInformation(Bank bank, BankDto loan) {
        bank.setTypeOfReturn(Bank.guessTypeOfReturn(loan.getTypeOfReturn()));
        bank.setId(loan.getId());
        setExpenses(bank, loan);
        bank.setAlreadyPaid(BigDecimal.ZERO);
        bank.setRemainMoney(loan.getFullLoanCoeff());
        bank.setTookMoney(loan.getTookMoney());
        bank.setDateOfLoan(dayIntermediateService.findByPresent());
        var remainsDayDate = Bank.guessRemainsDate(bank.getDateOfLoan(), loan).getDate();
        var remainsDay = dayIntermediateService.findByDate(remainsDayDate);
        bank.setRemainsDate(remainsDay);
    }

    private void setExpenses(Bank bank, BankDto loan) {
        bank.setPayPerDay(BigDecimal.ZERO);
        bank.setPayPerWeek(BigDecimal.ZERO);
        bank.setPayPerMonth(BigDecimal.ZERO);

        if(bank.getTypeOfReturn().equals(Bank.TypeOfReturn.PER_DAY))
            bank.setPayPerDay(loan.getPercentDay());
        else if(bank.getTypeOfReturn().equals(Bank.TypeOfReturn.PER_WEEK))
            bank.setPayPerWeek(loan.getPercentWeek());
        else bank.setPayPerMonth(loan.getPercentMonth());
    }

    public LoanDto getLoanDtoFromIntermediateEntity(Bank b) {
        LoanDto loanDto = new LoanDto();
        loanDto.setId(b.getId());
        loanDto.setTitle(b.getTitle());
        loanDto.setLoanDate(
                calendarConverter.dateFormer(
                        b.getDateOfLoan().getDate()));
        loanDto.setRemainDate(
                calendarConverter.dateFormer(
                        b.getRemainsDate().getDate()));
        loanDto.setAlreadyPaid(b.getAlreadyPaid());
        loanDto.setRemainsToPay(b.getRemainMoney());
        loanDto.setAmount(b.getTookMoney());
        loanDto.setTor(b.getTypeOfReturn().toString());
        return loanDto;
    }
}
