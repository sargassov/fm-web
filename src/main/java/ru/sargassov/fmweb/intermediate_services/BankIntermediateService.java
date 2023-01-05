package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_entities.Bank;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_repositories.BankIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.BankIntermediateServiceSpi;

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
}
