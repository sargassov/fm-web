package ru.sargassov.fmweb.cheats.model;

import lombok.Data;
import ru.sargassov.fmweb.cheats.Cheat;



@Data
public class LeoMessiCheat extends Cheat {

    private final String code = "givemeleomessiinmyteam";
    private final String description = "You invoked a Leo Messi!";

    @Override
    public void activate() {
        service.leoMessiCheatActivate();
    }
}
