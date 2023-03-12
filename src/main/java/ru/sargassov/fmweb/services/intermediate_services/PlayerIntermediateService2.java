package ru.sargassov.fmweb.services.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.repositories.intermediate_repositories.PlayerIntermediateRepository;
import ru.sargassov.fmweb.spi.intermediate_spi.PlayerIntermediateServiceSpi2;

import java.util.List;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class PlayerIntermediateService2 implements PlayerIntermediateServiceSpi2 {

    private final PlayerIntermediateRepository repository;

    @Override
    public List<Player> findByTeam(Team team) {
        return repository.findByTeam(team);
    }

    @Override
    public Player save(Player player) {
        return repository.save(player);
    }

    @Override
    public Player findCaptainByTeam(Team userTeam) {
        return repository.findCapitanFromTeam(userTeam);
    }

    @Override
    public void remove(Player player) {
        repository.delete(player);
    }

    @Override
    public Integer guessNumber(Team team, Integer number) {
        var playerList = repository.findByTeam(team);
        while (true) {
            int finalNumber = number;
            var flag = playerList.stream().anyMatch(p -> p.getNumber() == finalNumber);
            if (!flag) {
                break;
            }
            number++;
            if(number == 100) {
                number = 1;
            }
        }
        return number;
    }
}
