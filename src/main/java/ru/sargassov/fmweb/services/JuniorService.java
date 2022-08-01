package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api_temporary_classes_group.JuniorPoolApi;
import ru.sargassov.fmweb.converters.JuniorConverter;
import ru.sargassov.fmweb.dto.player_dtos.JuniorDto;
import ru.sargassov.fmweb.dto.text_responses.TextResponse;
import ru.sargassov.fmweb.exceptions.YouthAcademyException;
import ru.sargassov.fmweb.intermediate_entites.Player;
import ru.sargassov.fmweb.intermediate_entites.Position;
import ru.sargassov.fmweb.intermediate_entites.Team;
import ru.sargassov.fmweb.repositories.JuniorRepository;
import ru.sargassov.fmweb.spi.JuniorServiceSpi;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class JuniorService implements JuniorServiceSpi {
    private final JuniorRepository juniorRepository;
    private final JuniorConverter juniorConverter;
    private final JuniorPoolApi juniorPoolApi;
    private final UserService userService;

    @Override
    @Transactional
    public void loadYouthList(){
        log.info("JuniorService.createYouthPool");
        juniorPoolApi.setYouthApiList(juniorRepository.findAll().stream()
                .map(juniorConverter::entityToString)
                .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public List<String> getNamesFromJuniorPoolApi(){
        return juniorPoolApi.getYouthApiList();
    }

    @Override
    @Transactional
    public Player getYoungPlayer(Position position){
        log.info("JuniorService.getYoungPlayer");
        Random random = new Random();
        Player player = new Player();
        int selected = random.nextInt(juniorPoolApi.getYouthApiList().size());

        String name = juniorPoolApi.getYouthPlayerName(selected);
        player.setName(name);
        player.setPosition(position);
        juniorConverter.nameToIntermediateEntity(player);


        return player;
    }

    @Override
    @Transactional
    public TextResponse isUserVisitedYouthAcademyToday() {
        return userService.isUserVisitedYouthAcademyToday();
    }


    @Override
    @Transactional
    public List<JuniorDto> getRandomFiveYoungPlyers() {
        return juniorPoolApi.getRandomFiveYoungPlyers().stream()
                .map(juniorConverter::nameToJuniorDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TextResponse invokeYoungPlayerInUserTeam(JuniorDto juniorDto) {
        Team team = userService.getUserTeam();
        if(team.getWealth().compareTo(juniorDto.getPrice()) < 0){
            try{
                throw new YouthAcademyException("The wealth of User team is less than young player price!");
            } catch (YouthAcademyException y){
                log.error(y.getMessage());
                return new TextResponse(y.getMessage());
            }
        }
        if(userService.isVisited()) {
            throw new YouthAcademyException("");
        }

        Player p = juniorConverter.juniorDtoToIntermediateEntityPlayer(juniorDto);
        team.setWealth(team.getWealth().subtract(p.getPrice()));
        team.getPlayerList().add(p);
        juniorPoolApi.deleteFromApiList(juniorDto.getName());
        userService.visit();
        return new TextResponse("Player " + p.getName() + " was invoked in Your team");
    }
}
