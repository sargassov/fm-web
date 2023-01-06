package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.UserData;
import ru.sargassov.fmweb.intermediate_entities.User;

@Component
@AllArgsConstructor
@Slf4j
public class UserConverter {

    public User parseUserData(UserData userData){
        var user = new User();
        user.setName(userData.getLastname() + " " + userData.getName());
        user.setAbout(userData.getHomeTown());
        user.setPassword(userData.getPassword());
        return user;
    }
}
