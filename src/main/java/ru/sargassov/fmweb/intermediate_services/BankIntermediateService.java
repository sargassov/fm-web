package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_entities.Bank;
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
    public void save(List<Bank> banks) {
        repository.saveAll(banks);
    }
}
