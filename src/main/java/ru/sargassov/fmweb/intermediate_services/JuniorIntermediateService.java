package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.PlayerConverter;
import ru.sargassov.fmweb.intermediate_entities.Junior;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Position;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_repositories.JuniorIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.JuniorIntermediateServiceSpi;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class JuniorIntermediateService implements JuniorIntermediateServiceSpi {

    private PlayerConverter playerConverter;

    private JuniorIntermediateRepository repository;
    @Override
    public void save(List<Junior> juniorPlayers) {
        repository.saveAll(juniorPlayers);
    }

    @Override
    public Player getYoungPlayerForPosition(Position currentPosition, User user, List<Junior> allJuniors) {
        log.info("JuniorService.getYoungPlayer"); //TODO разобраться
        var random = new Random();
        var allJuniorsSize = allJuniors.size();
        var selected = random.nextInt(allJuniorsSize);
        var junior = getRandomYoungPlayer(selected, allJuniors);
        return playerConverter.getIntermadiateEntityFromJunior(junior, currentPosition, user);
    }

    @Override
    public List<Junior> findByUser(User user) {
        return repository.findByUser(user);
    }

    private Junior getRandomYoungPlayer(int selected, List<Junior> allJuniors) {
        var junior = allJuniors.get(selected);
        repository.deleteById(junior.getId());
        allJuniors.remove(junior);
        return junior;
    }

    private Junior getAndDeleteById(Junior junior) {
        repository.deleteById(junior.getId());
        return junior;
    }
}
