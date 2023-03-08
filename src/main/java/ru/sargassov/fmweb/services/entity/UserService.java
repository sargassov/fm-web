package ru.sargassov.fmweb.services.entity;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.UserConverter;
import ru.sargassov.fmweb.dto.UserData;
import ru.sargassov.fmweb.intermediate_entities.HeadCoach;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.spi.intermediate_spi.HeadCoachIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.TeamIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.UserIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.entity.UserServiceSpi;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements UserServiceSpi {
    private final UserIntermediateServiceSpi userIntermediateService;
    private final TeamIntermediateServiceSpi teamIntermediateService;
    private final HeadCoachIntermediateServiceSpi headCoachIntermediateService;
    private final UserConverter userConverter;

    @Override
    @Transactional
    public User constructNewUser(UserData userData){
        var name = userData.getName();
        log.info("UserService.constructNewUser " + name);
        var userWithoutId = userConverter.parseUserData(userData);
        var user = userIntermediateService.findByUserData(userData);
        if (user != null) {
            throw new IllegalStateException("User " + user.getName() + " already exists");
        }
        return userIntermediateService.save(userWithoutId);
    }

//    @Override
//    @Transactional
//    public boolean isVisited(){
//        return userApi.isVisitToYouthAcademyToday();
//    }
//
//    @Override
//    @Transactional
//    public TextResponse isUserVisitedYouthAcademyToday() {
//        log.info("UserService.isUserVisitedYouthAcademyToday");
//        boolean visited = userApi.isVisitToYouthAcademyToday();
//        if(visited)
//            try{
//                log.error("USER HAD ALREADY VISITED IN YOUTH ACADEMY");
//                throw new YouthAcademyException("You have already were in Youth Academy today");
//            } catch (YouthAcademyException y){
//                return new TextResponse(y.getMessage());
//            }
//        else
//            return new TextResponse("Now, you can see new players from your club's academy.");
//    }
//
//    @Override
//    @Transactional
//    public Team getUserTeam() {
//        return userApi.getUserTeam();
//    }
//
//    @Override
//    @Transactional
//    public Player getPlayerByNameFromUserTeam(String name) {
//        return userApi.getPlayerByNameFromUserTeam(name);
//    }
//
//    @Override
//    @Transactional
//    public Player getPlayerByNumberFromUserTeam(Integer integer) {
//        return userApi.getPlayerByNumberFromUserTeam(integer);
//    }
//
//    @Override
//    @Transactional
//    public Player getCaptainOfUserTeam() {
//        return userApi.getCaptainOfUserTeam();
//    }
//
//    @Override
//    @Transactional
//    public void visit(boolean visit) {
//        userApi.setVisitToYouthAcademyToday(visit);
//    }
//
//    @Override
//    @Transactional
//    public List<Player> getPlayerListFromUserTeam() {return userApi.getTeam().getPlayerList();}
//
//    @Override
//    @Transactional
//    public List<Coach> getCoachListFromUserTeam() {return userApi.getTeam().getCoaches();}

    public void setUserTeam(UserData userData, User user) {
        var userTeamString = userData.getTeamName();
        var userTeam = teamIntermediateService.findByNameAndUser(userTeamString, user);
        var userName = user.getName();
        var headCoach = new HeadCoach(userName);
        user.setUserTeam(userTeam);
        userTeam.assignUser(user);
        headCoachIntermediateService.assignAndSave(user, headCoach);
        teamIntermediateService.save(userTeam);
        userIntermediateService.save(user);
    }
}
