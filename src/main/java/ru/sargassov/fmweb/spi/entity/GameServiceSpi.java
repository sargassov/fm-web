package ru.sargassov.fmweb.spi.entity;

import ru.sargassov.fmweb.dto.UserData;

public interface GameServiceSpi {
    void createNewGame(UserData userData);

    void loadGame(UserData userData);
}
