package ru.sargassov.fmweb.converters;

import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.entities.CupEntity;
import ru.sargassov.fmweb.intermediate_entities.Cup;
import ru.sargassov.fmweb.intermediate_entities.League;
import ru.sargassov.fmweb.intermediate_entities.User;

@Component
public class CupConverter {

    public Cup getIntermediateCupEntityFromEntity(CupEntity cupEntity, User user, League league) {
        var cup = new Cup();
        cup.setName(cupEntity.getName());
        cup.setLeague(league);
        cup.setTeamValue(cupEntity.getTeamValue());
        cup.setUser(user);
        return cup;
    }
}
