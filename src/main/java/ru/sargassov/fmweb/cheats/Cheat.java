package ru.sargassov.fmweb.cheats;

import lombok.Data;
import ru.sargassov.fmweb.services.CheatService;

@Data
public abstract class Cheat {
    protected String code;
    protected String description;
    protected CheatService service;
    public abstract void activate();
}
