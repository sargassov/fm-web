package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.dto.CoachDto;
import ru.sargassov.fmweb.dto.PriceResponce;
import ru.sargassov.fmweb.intermediate_entities.Coach;
import ru.sargassov.fmweb.intermediate_entities.Player;

import java.util.List;

public interface CoachServiceSpi {

    List<CoachDto> getAllCoachFromUserTeam();

    void newCoachPurchaseResponce();

    PriceResponce getPriceForNewCoach(CoachDto coachDto);

    void createNewCoach(CoachDto coachDto);

    List<Player> getBusyPlayers();

    List<Player> getPlayerComparingByNumberAndPositionOfCoach(Coach coach);

    void changePlayerOnTrain(CoachDto coachDto);

    void selectingPlayerOnTrain(List<Player> players, int countPlayerInList, Coach coach);

    void guessTrainingAble(Coach coach, Player player);

    void deleteCoachByCount(Integer count);

    void replaceCounters(List<Coach> coaches);

    void changeTrainingProgram(Integer count, Integer program);
}
