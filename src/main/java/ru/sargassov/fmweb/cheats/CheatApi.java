package ru.sargassov.fmweb.cheats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.exceptions.CheatException;

import java.util.List;

@Component
@AllArgsConstructor
@Getter
public class CheatApi {
    private List<Cheat> cheats;

    public Cheat findByCode(String code) {
        for (Cheat cheat : cheats) {
            if (cheat.getCode().equals(code)) {
                return cheat;
            }
        }
        throw new CheatException("Wrong cheat syntax!");
    }

    public void constructApi(List<Cheat> cheats) {
        this.cheats = cheats;
    }
}
