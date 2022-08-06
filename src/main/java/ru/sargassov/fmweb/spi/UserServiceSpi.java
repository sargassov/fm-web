package ru.sargassov.fmweb.spi;

import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.UserData;
import ru.sargassov.fmweb.dto.text_responses.TextResponse;
import ru.sargassov.fmweb.exceptions.YouthAcademyException;
import ru.sargassov.fmweb.intermediate_entites.Coach;
import ru.sargassov.fmweb.intermediate_entites.Player;
import ru.sargassov.fmweb.intermediate_entites.Team;

import java.util.List;


public interface UserServiceSpi {

    void fillUserApi(UserData userData);

    boolean isVisited();

    TextResponse isUserVisitedYouthAcademyToday();

    Team getUserTeam();

    Player getPlayerByNameFromUserTeam(String name);

    Player getPlayerByNumberFromUserTeam(Integer integer);

    Player getCaptainOfUserTeam();

    void visit();

    List<Player> getPlayerListFromUserTeam();

    List<Coach> getCoachListFromUserTeam();
}
