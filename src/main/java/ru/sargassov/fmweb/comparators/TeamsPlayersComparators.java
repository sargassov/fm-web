package ru.sargassov.fmweb.comparators;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.PlayerOnPagePlayersDto;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
@Getter
public class TeamsPlayersComparators {
    private List<Comparator<PlayerOnPagePlayersDto>> comparators = new ArrayList<>(List.of(
            Comparator.comparing(PlayerOnPagePlayersDto::getName),
            Comparator.comparing(PlayerOnPagePlayersDto::getClub),
            Comparator.comparing(PlayerOnPagePlayersDto::getNatio),
            Comparator.comparing(PlayerOnPagePlayersDto::getNumber),
            Comparator.comparing(PlayerOnPagePlayersDto::getPosition),
            Comparator.comparing(PlayerOnPagePlayersDto::getGkAble, Comparator.reverseOrder()),
            Comparator.comparing(PlayerOnPagePlayersDto::getDefAble, Comparator.reverseOrder()),
            Comparator.comparing(PlayerOnPagePlayersDto::getMidAble,Comparator.reverseOrder()),
            Comparator.comparing(PlayerOnPagePlayersDto::getForwAble, Comparator.reverseOrder()),
            Comparator.comparing(PlayerOnPagePlayersDto::getCaptainAble, Comparator.reverseOrder()),
            Comparator.comparing(PlayerOnPagePlayersDto::isInjury),
            Comparator.comparing(PlayerOnPagePlayersDto::getTrainingAble, Comparator.reverseOrder()),
            Comparator.comparing(PlayerOnPagePlayersDto::getBirthYear),
            Comparator.comparing(PlayerOnPagePlayersDto::getStrategyPlace),
            Comparator.comparing(PlayerOnPagePlayersDto::getPower, Comparator.reverseOrder()),
            Comparator.comparing(PlayerOnPagePlayersDto::getTire),
            Comparator.comparing(PlayerOnPagePlayersDto::getTimeBeforeTreat, Comparator.reverseOrder()),
            Comparator.comparing(PlayerOnPagePlayersDto::getPrice, Comparator.reverseOrder())

    ));
}
