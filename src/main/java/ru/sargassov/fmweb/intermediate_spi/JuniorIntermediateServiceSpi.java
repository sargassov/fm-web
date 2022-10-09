package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.Junior;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Position;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

public interface JuniorIntermediateServiceSpi {
    void save(List<Junior> juniorPlayers);

    Player getYoungPlayerForPosition(Position currentPosition, User user, List<Junior> allJuniors);

    List<Junior> findByUser(User user);
}
