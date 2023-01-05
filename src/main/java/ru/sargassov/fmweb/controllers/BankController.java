package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.sargassov.fmweb.dto.BankDto;
import ru.sargassov.fmweb.spi.BankServiceSpi;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class BankController {
    private final BankServiceSpi bankService;

    @GetMapping("/banks/loan_request/{parameter}")
    public List<BankDto> getAllPotencialLoans(@PathVariable Integer parameter) {
        log.info("BankService.getTour");
        return bankService.getAllPotencialLoans(parameter);
    }

    @PostMapping("/banks/finance/get")
    public void takeNewLoan(@RequestBody BankDto loan) {
        log.info("BankController.takeNewLoan");
        bankService.takeNewLoan(loan);
    }
}
