package ru.sargassov.fmweb.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.Bank;
import ru.sargassov.fmweb.services.BankService;

import java.util.List;

@Component
@AllArgsConstructor
@Getter
public class BankApi {
    private List<Bank> bankApiList;

    public void setBankApiList(List<Bank> bankApiList) {
        this.bankApiList = bankApiList;
    }
}
