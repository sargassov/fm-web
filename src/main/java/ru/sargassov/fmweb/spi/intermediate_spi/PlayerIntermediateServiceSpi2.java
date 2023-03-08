package ru.sargassov.fmweb.spi.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Team;

import java.util.List;

public interface PlayerIntermediateServiceSpi2 {

    List<Player> findByTeam(Team userTeam);

    Player save (Player player);

    Player findCaptainByTeam(Team userTeam);

    void remove(Player player);
}
