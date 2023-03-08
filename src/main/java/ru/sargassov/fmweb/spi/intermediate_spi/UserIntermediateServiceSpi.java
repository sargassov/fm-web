package ru.sargassov.fmweb.spi.intermediate_spi;

import ru.sargassov.fmweb.dto.UserData;
import ru.sargassov.fmweb.intermediate_entities.User;

public interface UserIntermediateServiceSpi {
    User save(User user);
    User findByUserData(UserData userData);
}
