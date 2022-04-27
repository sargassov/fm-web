package ru.sargassov.fmweb.api;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.Team;

@Component
@Data
public class UserApi {
    private String name;
    private String homeTown;
    private Team team;
}
