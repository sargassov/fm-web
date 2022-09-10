package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.controllers.JuniorController;
import ru.sargassov.fmweb.converters.JuniorConverter;
import ru.sargassov.fmweb.converters.PlayerConverter;
import ru.sargassov.fmweb.intermediate_entities.Junior;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Position;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_repositories.JuniorIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.JuniorIntermediateServiceSpi;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class JuniorIntermediateService implements JuniorIntermediateServiceSpi {

    private static int juniorPlayersValue = 650;

    private PlayerConverter playerConverter;

    private JuniorIntermediateRepository repository;
    @Override
    public void save(List<Junior> juniorPlayers) {
        repository.saveAll(juniorPlayers);
    }

    @Override
    public Player getYoungPlayerForPosition(Position currentPosition, User user) {
        log.info("JuniorService.getYoungPlayer");
        var random = new Random();
        var selected = (long) random.nextInt(juniorPlayersValue);
        Optional<Junior> optJunior;

        do {
            optJunior = repository.findById(selected);
            if (optJunior.isEmpty()) {
                selected++;
                if (selected == juniorPlayersValue) {
                    selected = 0;
                }
            }
        } while (optJunior.isEmpty());

        var junior = optJunior.get();
        return playerConverter.getIntermadiateEntityFromJunior(junior, currentPosition, user);
    }
}
