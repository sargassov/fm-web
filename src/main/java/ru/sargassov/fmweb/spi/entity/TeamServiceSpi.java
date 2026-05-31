package ru.sargassov.fmweb.spi.entity;

import ru.sargassov.fmweb.enums.PositionType;
import ru.sargassov.fmweb.intermediate_entities.*;

import java.util.List;

public interface TeamServiceSpi {

    void loadTeams(User user);

    List<Team> findAll(User user, League league);

    void fillTeams(List<Team> teamList, User user, League league);

    void juniorRecruitment(User user);

    void addJuniorToTeam(Team currentTeam, PositionType currentPosition, User user, List<Junior> allJuniors);

    int randomGuessNum(Team currentTeam);

    void addJuniorToTeam(Team sellerTeam, PositionType position);
}
