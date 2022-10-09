package ru.sargassov.fmweb.constants;

import lombok.Getter;
import lombok.Setter;
import ru.sargassov.fmweb.intermediate_entities.User;

@Getter
@Setter
public class UserHolder {

    private UserHolder() {
        throw new IllegalStateException("Utility class");
    }

    public static User user;


}
