package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_repositories.PlayerIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.PlayerIntermediateServiceSpi;

import java.util.List;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class PlayerIntermediateService implements PlayerIntermediateServiceSpi {

    private final PlayerIntermediateRepository repository;
    @Override
    public List<Player> save(List<Player> newPlayersWithoutIds) {
        return repository.saveAll(newPlayersWithoutIds);
    }

    @Override
    public Player save(Player player) {
        repository.save(player);
    }
}
