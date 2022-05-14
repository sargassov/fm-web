package ru.sargassov.fmweb.comparators;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.player_dtos.PlayerSoftSkillDto;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
@Getter
public class TeamsPlayersComparators {
    private List<Comparator<PlayerSoftSkillDto>> comparators = new ArrayList<>(List.of(
            Comparator.comparing(PlayerSoftSkillDto::getName),
            Comparator.comparing(PlayerSoftSkillDto::getClub),
            Comparator.comparing(PlayerSoftSkillDto::getNatio),
            Comparator.comparing(PlayerSoftSkillDto::getNumber),
            Comparator.comparing(PlayerSoftSkillDto::getPosition),
            Comparator.comparing(PlayerSoftSkillDto::getGkAble, Comparator.reverseOrder()),
            Comparator.comparing(PlayerSoftSkillDto::getDefAble, Comparator.reverseOrder()),
            Comparator.comparing(PlayerSoftSkillDto::getMidAble,Comparator.reverseOrder()),
            Comparator.comparing(PlayerSoftSkillDto::getForwAble, Comparator.reverseOrder()),
            Comparator.comparing(PlayerSoftSkillDto::getCaptainAble, Comparator.reverseOrder()),
            Comparator.comparing(PlayerSoftSkillDto::isInjury),
            Comparator.comparing(PlayerSoftSkillDto::getTrainingAble, Comparator.reverseOrder()),
            Comparator.comparing(PlayerSoftSkillDto::getBirthYear),
            Comparator.comparing(PlayerSoftSkillDto::getStrategyPlace),
            Comparator.comparing(PlayerSoftSkillDto::getPower, Comparator.reverseOrder()),
            Comparator.comparing(PlayerSoftSkillDto::getTire),
            Comparator.comparing(PlayerSoftSkillDto::getTimeBeforeTreat, Comparator.reverseOrder()),
            Comparator.comparing(PlayerSoftSkillDto::getPrice, Comparator.reverseOrder())

    ));
}
