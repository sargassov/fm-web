package ru.sargassov.fmweb.cheats.model;

import lombok.Getter;
import lombok.Setter;
import ru.sargassov.fmweb.cheats.Cheat;

@Getter
@Setter
public class LeoMessiCheat extends Cheat {

    private final String code = "givemeleomessiinmyteam";
    private final String description = "You invoked a Leo Messi!";

    @Override
    public void activate() {
        service.leoMessiCheatActivate();
    }
}
