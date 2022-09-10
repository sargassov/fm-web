package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_repositories.PlayerIntermediateRepository;

import java.util.List;

public interface PlayerIntermediateServiceSpi {

    List<Player> save(List<Player> newPlayersWithoutIds);

    Player save(Player player);
}
