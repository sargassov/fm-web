package ru.sargassov.fmweb.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.BankConverter;
import ru.sargassov.fmweb.dto.BankDto;
import ru.sargassov.fmweb.repositories.BankRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankService {
    private final BankRepository bankRepository;
    private final BankConverter bankConverter;

    public List<BankDto> getAllBanks(){
        log.info("BanksService.getAllBanks");
        return bankRepository.findAll().stream()
                .map(bankConverter::entityToDto).collect(Collectors.toList());
    }
}
