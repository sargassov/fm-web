package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api.JuniorPoolApi;
import ru.sargassov.fmweb.converters.JuniorConverter;
import ru.sargassov.fmweb.intermediate_entites.Player;
import ru.sargassov.fmweb.intermediate_entites.Position;
import ru.sargassov.fmweb.repositories.JuniorRepository;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class JuniorService {
    private final JuniorRepository juniorRepository;
    private final JuniorConverter juniorConverter;
    private final JuniorPoolApi juniorPoolApi;

    public void loadYouthList(){
        log.info("JuniorService.createYouthPool");
        juniorPoolApi.setYouthApiList(juniorRepository.findAll().stream()
                .map(juniorConverter::entityToString)
                .collect(Collectors.toList()));
    }

    public List<String> getNamesFromJuniorPoolApi(){
        return juniorPoolApi.getYouthApiList();
    }

    public Player getYoungPlayer(Position position){
        log.info("JuniorService.getYoungPlayer");
        Random random = new Random();
        Player player = new Player();
        int selected = random.nextInt(juniorPoolApi.getYouthApiList().size());

        String name = juniorPoolApi.getYouthPlayerName(selected);
        player.setName(name);
        player.setPosition(position);
        juniorConverter.nameToPlayerDto(player);


        return player;
    }
}
