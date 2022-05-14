package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.api.TeamApi;
import ru.sargassov.fmweb.api.UserApi;
import ru.sargassov.fmweb.intermediate_entites.HeadCoach;
import ru.sargassov.fmweb.dto.UserData;
import ru.sargassov.fmweb.services.TeamService;

@Component
@AllArgsConstructor
@Slf4j
public class UserConverter {
    private final UserApi userApi;
    private final TeamApi teamApi;


    public void userDataToApi(UserData userData){
        userApi.setName(userData.getLastname() + " " + userData.getName());
        userApi.setHomeTown(userData.getHomeTown());
        userApi.setTeam(teamApi.findByName(userData.getTeamName()));
        userApi.getTeam().setHeadCoach(new HeadCoach(userApi.getName()));
        userApi.setVisitToYouthAcademyToday(false);
    }
}
