package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.UserData;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Component
@AllArgsConstructor
@Slf4j
public class UserConverter {


    public User parseUserData(UserData userData){
        var user = new User();
        user.setName(userData.getLastname() + " " + userData.getName());
        user.setAbout(userData.getHomeTown());
//        user.setTeam(teamApi.findByName(userData.getTeamName()));
//        userApi.getTeam().setHeadCoach(new HeadCoach(userApi.getName()));
//        userApi.setVisitToYouthAcademyToday(false);
        return user;
    }



    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "about")
    private String about;

    @OneToOne
    @JoinColumn(name = "id_team")
    private Team userTeam;
}
