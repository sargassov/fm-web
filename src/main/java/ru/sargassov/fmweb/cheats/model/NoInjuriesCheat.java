package ru.sargassov.fmweb.cheats.model;

import lombok.Data;
import ru.sargassov.fmweb.cheats.Cheat;

@Data
public class NoInjuriesCheat extends Cheat {

    private final String code = "ihavenoinjuriesnowandever";
    private final String description = "All your player are resored their health!";

    @Override
    public void activate() {
        service.noInjuriesCheatActivate();
    }
}
