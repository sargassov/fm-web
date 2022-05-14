package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api.UserApi;
import ru.sargassov.fmweb.converters.UserConverter;
import ru.sargassov.fmweb.dto.TextResponce;
import ru.sargassov.fmweb.dto.UserData;
import ru.sargassov.fmweb.exceptions.YouthAcademyException;
import ru.sargassov.fmweb.intermediate_entites.Player;
import ru.sargassov.fmweb.intermediate_entites.Team;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserApi userApi;
    private final UserConverter userConverter;

    public void fillUserApi(UserData userData){
        log.info("UserService.fillUserApi " + userData.getLastname() + " " + userData.getName());
        userConverter.userDataToApi(userData);
    }

    public boolean isVisited(){
        return userApi.isVisitToYouthAcademyToday();
    }

    public TextResponce isUserVisitedYouthAcademyToday() {
        log.info("UserService.isUserVisitedYouthAcademyToday");
        boolean visited = userApi.isVisitToYouthAcademyToday();
        if(visited)
            try{
                log.error("USER HAD ALREADY VISITED IN YOUTH ACADEMY");
                throw new YouthAcademyException("You have already were in Youth Academy today");
            } catch (YouthAcademyException y){
                return new TextResponce(y.getMessage());
            }
        else
            return new TextResponce("Now, you can see new players from your club's academy.");
    }

    public Team getUserTeam() {
        return userApi.getUserTeam();
    }

    public Player getPlayerByNameFromUserTeam(String name) {
        return userApi.getPlayerByNameFromUserTeam(name);
    }

    public Player getPlayerByNumberFromUserTeam(Integer integer) {
        return userApi.getPlayerByNumberFromUserTeam(integer);
    }

    public Player getCaptainOfUserTeam() {
        return userApi.getCaptainOfUserTeam();
    }

    public void visit() {
        userApi.setVisitToYouthAcademyToday(true);
    }
}
