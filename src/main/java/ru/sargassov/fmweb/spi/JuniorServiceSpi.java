package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.dto.player_dtos.JuniorDto;
import ru.sargassov.fmweb.dto.text_responses.TextResponse;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Position;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

public interface JuniorServiceSpi {

    void loadYouthList(User user);

//    List<String> getNamesFromJuniorPoolApi();
//
//    Player getYoungPlayer(Position position);
//
//    TextResponse isUserVisitedYouthAcademyToday();
//
//    List<JuniorDto> getRandomFiveYoungPlyers();

//    TextResponse invokeYoungPlayerInUserTeam(JuniorDto juniorDto);
}
