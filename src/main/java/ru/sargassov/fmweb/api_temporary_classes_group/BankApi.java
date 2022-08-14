package ru.sargassov.fmweb.api_temporary_classes_group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.exceptions.BankNotFoundException;
import ru.sargassov.fmweb.intermediate_entites.Bank;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@Getter
public class BankApi {
    private List<Bank> bankApiList; //TEMPORARY CLASS

    public void setBankApiList(List<Bank> bankApiList) {
        this.bankApiList = bankApiList;
    }

    public Bank getBankByTitleFromApiList(String title) {
        return bankApiList.stream()
                .filter(b -> b.getTitle().equals(title))
                .findFirst()
                .orElseThrow(() ->
                        new BankNotFoundException("Bank " + title + " not found!"));
    }

    public void inactive(String title) {
       Bank bank = getBankByTitleFromApiList(title);
       bank.setActive(false);
    }

    public void active(Bank bank) {
        Bank b = getBankByTitleFromApiList(bank.getTitle());
        b.setActive(true);
    }
}
