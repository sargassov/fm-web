package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.JuniorConverter;
import ru.sargassov.fmweb.entity_repositories.JuniorRepository;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_spi.JuniorIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.JuniorServiceSpi;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class JuniorService implements JuniorServiceSpi {
    private final JuniorRepository juniorRepository;
    private final JuniorConverter juniorConverter;
    private final JuniorIntermediateServiceSpi juniorIntermediateService;

    @Override
    @Transactional
    public void loadYouthList(User user){
        log.info("JuniorService.loadYouthList");
        var juniorPlayerNames = juniorRepository.findAll();
        var juniorPlayers = juniorPlayerNames
                .stream()
                .map(jp -> juniorConverter.getIntermediateEntityFromEntity(jp, user))
                .collect(Collectors.toList());
        juniorIntermediateService.save(juniorPlayers);
    }

//    @Override
//    @Transactional
//    public List<String> getNamesFromJuniorPoolApi(){
//        return juniorPoolApi.getYouthApiList();
//    }

//    @Override
//    @Transactional
//    public Player getYoungPlayer(Position position){
//        log.info("JuniorService.getYoungPlayer");
//        Random random = new Random();
//        Player player = new Player();
//        int selected = random.nextInt(juniorPoolApi.getYouthApiList().size());
//
//        String name = juniorPoolApi.getYouthPlayerName(selected);
//        player.setName(name);
//        player.setPosition(position);
//        juniorConverter.setSkillForYoungPlayerIntermediateEntity(player);
//
//
//        return player;
//    }
}
