package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.dto.UserData;
import ru.sargassov.fmweb.intermediate_entities.User;


public interface UserServiceSpi {

    User constructNewUser(UserData userData);

//    boolean isVisited();
//
//    TextResponse isUserVisitedYouthAcademyToday();
//
//    Team getUserTeam();
//
//    Player getPlayerByNameFromUserTeam(String name);
//
//    Player getPlayerByNumberFromUserTeam(Integer integer);
//
//    Player getCaptainOfUserTeam();
//
//    void visit(boolean visit);
//
//    List<Player> getPlayerListFromUserTeam();
//
//    List<Coach> getCoachListFromUserTeam();

}
