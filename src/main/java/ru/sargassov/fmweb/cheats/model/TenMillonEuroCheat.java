package ru.sargassov.fmweb.cheats.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.sargassov.fmweb.cheats.Cheat;
import ru.sargassov.fmweb.spi.UserServiceSpi;


@Getter
@Setter
public class TenMillonEuroCheat extends Cheat {

    private final String code = "iwanttolargemoneyy";
    private final String description = "Your club activated pleasant bonus 10.000.000";

    private UserServiceSpi userService;

    @Override
    public void activate() {
//        service.tenMillionEuroCheat();
    }
}
