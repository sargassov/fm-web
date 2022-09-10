package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api_temporary_classes_group.UserApi;
import ru.sargassov.fmweb.converters.UserConverter;
import ru.sargassov.fmweb.dto.text_responses.TextResponse;
import ru.sargassov.fmweb.dto.UserData;
import ru.sargassov.fmweb.exceptions.YouthAcademyException;
import ru.sargassov.fmweb.intermediate_entities.Coach;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_spi.TeamIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.UserIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.UserServiceSpi;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements UserServiceSpi {
    private final UserIntermediateServiceSpi userIntermediateService;
    private final TeamIntermediateServiceSpi teamIntermediateService;
    private final UserConverter userConverter;

    @Override
    @Transactional
    public User constructNewUser(UserData userData){
        log.info("UserService.constructNewUser " + userData.getLastname() + " " + userData.getName());
        var userWithoutId = userConverter.parseUserData(userData);
        return userIntermediateService.save(userWithoutId);
    }

    @Override
    @Transactional
    public boolean isVisited(){
        return userApi.isVisitToYouthAcademyToday();
    }

    @Override
    @Transactional
    public TextResponse isUserVisitedYouthAcademyToday() {
        log.info("UserService.isUserVisitedYouthAcademyToday");
        boolean visited = userApi.isVisitToYouthAcademyToday();
        if(visited)
            try{
                log.error("USER HAD ALREADY VISITED IN YOUTH ACADEMY");
                throw new YouthAcademyException("You have already were in Youth Academy today");
            } catch (YouthAcademyException y){
                return new TextResponse(y.getMessage());
            }
        else
            return new TextResponse("Now, you can see new players from your club's academy.");
    }

    @Override
    @Transactional
    public Team getUserTeam() {
        return userApi.getUserTeam();
    }

    @Override
    @Transactional
    public Player getPlayerByNameFromUserTeam(String name) {
        return userApi.getPlayerByNameFromUserTeam(name);
    }

    @Override
    @Transactional
    public Player getPlayerByNumberFromUserTeam(Integer integer) {
        return userApi.getPlayerByNumberFromUserTeam(integer);
    }

    @Override
    @Transactional
    public Player getCaptainOfUserTeam() {
        return userApi.getCaptainOfUserTeam();
    }

    @Override
    @Transactional
    public void visit(boolean visit) {
        userApi.setVisitToYouthAcademyToday(visit);
    }

    @Override
    @Transactional
    public List<Player> getPlayerListFromUserTeam() {return userApi.getTeam().getPlayerList();}

    @Override
    @Transactional
    public List<Coach> getCoachListFromUserTeam() {return userApi.getTeam().getCoaches();}

    public void setUserTeam(User user) {
        var userTeamString = user.getUserTeamDescription();
        var userTeam = teamIntermediateService.findByName(userTeamString);
        user.setUserTeam(userTeam);
        userTeam.assignUser(user);
        teamIntermediateService.save(userTeam);
    }
}
