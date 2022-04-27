package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api.UserApi;
import ru.sargassov.fmweb.converters.UserConverter;
import ru.sargassov.fmweb.dto.UserData;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private UserApi userApi;
    private final UserConverter userConverter;

    public void fillUserApi(UserData userData){
        log.info("UserService.fillUserApi " + userData.getLastname() + " " + userData.getName());
        userConverter.userDataToApi(userData);
    }
}
