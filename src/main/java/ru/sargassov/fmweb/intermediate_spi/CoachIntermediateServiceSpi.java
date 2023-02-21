package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.dto.CoachDto;
import ru.sargassov.fmweb.dto.PriceResponce;
import ru.sargassov.fmweb.intermediate_entities.Coach;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Team;

import java.util.List;

public interface CoachIntermediateServiceSpi {

    Coach save(Coach coach);

    List<CoachDto> getAllCoachFromUserTeam();

    void newCoachPurchaseResponse();

    void createNewCoach(CoachDto coachDto);

    PriceResponce getPriceForNewCoach(CoachDto coachDto);

    void changePlayerOnTrain(CoachDto coachDto);

    List<Player> getPlayerComparingByNumberAndPositionOfCoach(Coach coach);

    List<Player> getBusyPlayers(List<Coach> coaches);

    void selectingPlayerOnTrain(List<Player> players, int countPlayerInList, Coach coach, Team userTeam);

    void guessTrainingAble(Coach coach, Player player);

    void deleteByid(Long count);

    List<Coach> findByTeam(Team userTeam);

    void changeTrainingProgram(Long id, Integer program);
}
