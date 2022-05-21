package ru.sargassov.fmweb.api_temporary_classes_group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.intermediate_entites.Bank;

import java.util.List;

@Component
@AllArgsConstructor
@Getter
public class BankApi {
    private List<Bank> bankApiList; //TEMPORARY CLASS

    public void setBankApiList(List<Bank> bankApiList) {
        this.bankApiList = bankApiList;
    }
}
