package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.dto.FinalPayment;
import ru.sargassov.fmweb.intermediate_entities.Bank;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_repositories.BankIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.BankIntermediateServiceSpi;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class BankIntermediateService implements BankIntermediateServiceSpi {

    private BankIntermediateRepository repository;

    @Override
    public List<Bank> save(List<Bank> banks) {
        return repository.saveAll(banks);
    }

    @Override
    public Bank save(Bank bank) {
        return repository.save(bank);
    }

    @Override
    public List<Bank> findAllByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    public Bank findById(long id) {
        return repository.getById(id);
    }

    @Override
    @Transactional
    public List<String> paymentPeriod(Bank bank, Bank.TypeOfReturn type, Team team, FinalPayment finalPayment) {
        var notesOfChanges = new ArrayList<String>();
        BigDecimal payType;

        switch (type) {
            case PER_DAY:
                payType = bank.getPayPerDay();
                break;
            case PER_WEEK:
                payType = bank.getPayPerWeek();
                break;
            default:
                payType = bank.getPayPerMonth();
        }

        var remainMoney = bank.getRemainMoney();
        var title = bank.getTitle();
        if (remainMoney.compareTo(payType) < 0){
            finalPayment.setFinal(true);
            lastPayment(team, bank);
            notesOfChanges.add(payType + " Euro was paid to the Bank " + title + ". It is " + type);
            notesOfChanges.add(title + " is closed!");
            return notesOfChanges;
        }

        team.setWealth(team.getWealth().subtract(payType));
        bank.setAlreadyPaid(bank.getAlreadyPaid().add(payType));
        bank.setRemainMoney(bank.getRemainMoney().subtract(payType));
        notesOfChanges.add(payType + " Euro was paid to the Bank " + title + ". It is " + type);
        return notesOfChanges;
    }

    @Override
    public List<Bank> findByTeam(Team team) {
        return repository.findByTeam(team);
    }

    @Transactional
    public void lastPayment(Team team, Bank bank) {
        var remainMoney = bank.getRemainMoney();
        team.setWealth(team.getWealth().subtract(remainMoney));
        team.getLoans().remove(bank);
        bank.remainLoan();
        repository.save(bank);
    }
}
