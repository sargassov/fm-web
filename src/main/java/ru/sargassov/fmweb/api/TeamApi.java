package ru.sargassov.fmweb.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.Team;
import ru.sargassov.fmweb.exceptions.TeamNotFoundException;
import ru.sargassov.fmweb.services.TeamService;

import java.util.List;

@Component
@Getter
@AllArgsConstructor
@Slf4j
public class TeamApi {
    private List<Team> teamApiList;

    public void setTeamApiList(List<Team> teamApiList) {
        this.teamApiList = teamApiList;
    }

    public Team findByName(String name){
        return teamApiList.stream()
                .filter(t -> t.getName().equals(name))
                .findFirst()
                .orElseThrow(() ->
                new TeamNotFoundException(String.format("Team with name = %s not found", name)));
    }

    public Team findTeamById(Long id){
        return teamApiList.stream()
                .filter(t -> t.getId() == id)
                .findFirst().orElseThrow(() ->
                        new TeamNotFoundException(String.format("Team with id = '%s' not found", id)));
    }

    public Team findUserTeam(String name){
        log.info("TeamApi.findUserTeam()");
        return teamApiList.stream()
                .filter(t -> t.getName().equals(name))
                .findFirst().orElseThrow(() ->
                        new TeamNotFoundException(String.format("Userteam = '%s' not found", name)));
    }

}
