package ru.sargassov.fmweb.api;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.exceptions.PlayerNotFoundException;
import ru.sargassov.fmweb.exceptions.TeamNotFoundException;
import ru.sargassov.fmweb.intermediate_entites.Player;
import ru.sargassov.fmweb.intermediate_entites.Team;

@Component
@Data
public class UserApi {
    private String name;
    private String homeTown;
    private Team team;

    public Player getPlayerByNumberFromUserTeam(int num){
        return team.getPlayerList()
                .stream()
                .filter(p -> p.getNumber() == num)
                .findFirst()
                .orElseThrow(() ->
                        new PlayerNotFoundException(String.format("Player with number = %d not found", num)));
    }

    public Player getPlayerByNameFromUserTeam(String name){
        return team.getPlayerList()
                .stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElseThrow(() ->
                        new PlayerNotFoundException(String.format("Player with name = %s not found", name)));
    }

    public Player getCaptainOfUserTeam(){
        return team.getPlayerList()
                .stream()
                .filter(p -> p.isCapitan())
                .findFirst()
                .orElseThrow(() ->
                        new PlayerNotFoundException("Team hasn't a captain"));
    }

    public Team getUserTeam() {
        return team;
    }
}
