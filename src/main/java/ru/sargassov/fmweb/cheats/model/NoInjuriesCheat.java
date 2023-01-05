package ru.sargassov.fmweb.cheats.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.sargassov.fmweb.cheats.Cheat;

@Getter
@Setter
public class NoInjuriesCheat extends Cheat {

    private final String code = "ihavenoinjuriesnowandever";
    private final String description = "All your player are resored their health!";

    @Override
    public void activate() {
        service.noInjuriesCheatActivate();
    }
}
