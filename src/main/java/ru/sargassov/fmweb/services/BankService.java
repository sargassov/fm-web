package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api_temporary_classes_group.BankApi;
import ru.sargassov.fmweb.converters.BankConverter;
import ru.sargassov.fmweb.intermediate_entites.Bank;
import ru.sargassov.fmweb.repositories.BankRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BankService {
    private final BankRepository bankRepository;
    private final BankConverter bankConverter;
    private final BankApi bankApi;

    public void loadBanks(){
        log.info("BanksService.getAllBanks");
        bankApi.setBankApiList(bankRepository.findAll().stream()
                .map(bankConverter::entityToDto).collect(Collectors.toList()));
    }

    public List<Bank> getBanksFromBankApi(){
        return bankApi.getBankApiList();
    }
}
