package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.dto.UserData;
import ru.sargassov.fmweb.dto.text_responses.TextResponse;
import ru.sargassov.fmweb.intermediate_entities.Coach;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;


public interface UserServiceSpi {

    User constructNewUser(UserData userData);

    boolean isVisited();

    TextResponse isUserVisitedYouthAcademyToday();

    Team getUserTeam();

    Player getPlayerByNameFromUserTeam(String name);

    Player getPlayerByNumberFromUserTeam(Integer integer);

    Player getCaptainOfUserTeam();

    void visit(boolean visit);

    List<Player> getPlayerListFromUserTeam();

    List<Coach> getCoachListFromUserTeam();

}
