package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.dto.player_dtos.JuniorDto;
import ru.sargassov.fmweb.dto.text_responses.TextResponse;
import ru.sargassov.fmweb.intermediate_entites.Player;
import ru.sargassov.fmweb.intermediate_entites.Position;

import java.util.List;

public interface JuniorServiceSpi {

    void loadYouthList();

    List<String> getNamesFromJuniorPoolApi();

    Player getYoungPlayer(Position position);

    TextResponse isUserVisitedYouthAcademyToday();

    List<JuniorDto> getRandomFiveYoungPlyers();

    TextResponse invokeYoungPlayerInUserTeam(JuniorDto juniorDto);
}
