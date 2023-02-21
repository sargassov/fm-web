package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.converters.CoachConverter;
import ru.sargassov.fmweb.dto.CoachDto;
import ru.sargassov.fmweb.dto.PriceResponce;
import ru.sargassov.fmweb.exceptions.CoachException;
import ru.sargassov.fmweb.intermediate_entities.Coach;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_repositories.CoachIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.CoachIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.TeamIntermediateServiceSpi2;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.sargassov.fmweb.constants.Constant.MAX_TIRE_FOR_TRAIN;
import static ru.sargassov.fmweb.intermediate_entities.Coach.CoachProgram.STANDART;
import static ru.sargassov.fmweb.intermediate_entities.Team.MAX_VALUE_OF_COACHES;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class CoachIntermediateService implements CoachIntermediateServiceSpi {

    private final CoachConverter coachConverter;
    private final CoachIntermediateRepository repository;
    private final TeamIntermediateServiceSpi2 teamIntermediateService;
    private static final String CANT_PURCHASE_MORE_COACHES = "You have 6 coaches from 6. You can't purchase more";
    private static final String HAVENT_MONEY_TO_ADD_THIS_COACH = "Your club haven't money to add this coach";
    private static final String NONE = "N/N";

    @Override
    public Coach save(Coach coach) {
        return repository.save(coach);
    }

    @Override
    @Transactional
    public List<CoachDto> getAllCoachFromUserTeam() {
        var user  = UserHolder.user;
        var userTeamId = user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        return userTeam.getCoaches().stream()
                .map(coachConverter::getCoachDtoFromIntermediateEntity)
                .sorted(Comparator.comparing(CoachDto::getId))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void newCoachPurchaseResponse() {
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        if (userTeam.getCoaches().size() == MAX_VALUE_OF_COACHES)
            throw new CoachException(CANT_PURCHASE_MORE_COACHES);
    }

    @Override
    @Transactional
    public void createNewCoach(CoachDto coachDto) {
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        var coach = coachConverter.getIntermediateEntityFromCoachDto(coachDto);
        var coachPrice = coach.getPrice();
        coach.setTeam(userTeam);
        newCoachPurchaseResponse();

        if (coachPrice.compareTo(userTeam.getWealth()) > 0) {
            throw new CoachException(HAVENT_MONEY_TO_ADD_THIS_COACH);
        }

        var coaches = userTeam.getCoaches();
        userTeam.setWealth(userTeam.getWealth().subtract(coachPrice));
        userTeam.substractPersonalExpenses(coachPrice);
        coaches.add(save(coach));
        teamIntermediateService.save(userTeam);
    }

    @Override
    @Transactional
    public PriceResponce getPriceForNewCoach(CoachDto coachDto) {
        return new PriceResponce(coachDto.guessPriceResponce());
    }

    @Override
    @Transactional
    public void changePlayerOnTrain(CoachDto coachDto) {
        var coachId = coachDto.getId();
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        var coach = userTeam.getCoachById(coachId);
        var players = getPlayerComparingByNumberAndPositionOfCoach(coach);
        Player player;

        coach.setCoachProgram(STANDART);
        coach.setTrainingAble(0);


        if (coachDto.getPlayerOnTraining().equals(NONE)){
            int countPlayerInList = 0;
            selectingPlayerOnTrain(players, countPlayerInList, coach, userTeam);
            return;
        }

        player = userTeam.findPlayerByName(coachDto.getPlayerOnTraining());
        var countPlayerInList = players.indexOf(player);
        selectingPlayerOnTrain(players, countPlayerInList, coach, userTeam);
    }

    @Override
    @Transactional
    public List<Player> getPlayerComparingByNumberAndPositionOfCoach(Coach coach){
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        return userTeam.getPlayerList().stream()
                .filter(p -> p.getPosition().equals(coach.getPosition()))
                .sorted(Comparator.comparing(Player::getNumber))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<Player> getBusyPlayers(List<Coach> coaches) {
        return coaches.stream()
                .map(Coach::getPlayerOnTraining)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void selectingPlayerOnTrain(List<Player> players, int countPlayerInList, Coach coach, Team userTeam) {
        var coaches = userTeam.getCoaches();
        for(int x = 0; x < players.size(); x++){
            var countPlayer = players.get(countPlayerInList);
            var busyPLayers = getBusyPlayers(coaches);
            if (busyPLayers.contains(countPlayer)
                    || players.get(countPlayerInList).getTire() > MAX_TIRE_FOR_TRAIN){
                countPlayerInList++;
                if(countPlayerInList == players.size()) {
                    countPlayerInList = 0;
                }
                continue;
            }
            break;
        }
        coach.setPlayerOnTraining(players.get(countPlayerInList));
        guessTrainingAble(coach, coach.getPlayerOnTraining());
    }

    @Override
    @Transactional
    public void guessTrainingAble(Coach coach, Player player) {
        double possibleTrainingAble = 1.0;
        possibleTrainingAble *= coach.getTrainingCoeff();
        coach.setTrainingAble((int) (possibleTrainingAble * player.getTrainingAble()));
    }

    @Override
    @Transactional
    public void deleteByid(Long id) {
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        var userTeamCoaches = userTeam.getCoaches();
        userTeamCoaches.removeIf(c -> c.getId().equals(id));
        repository.deleteById(id);
    }

    @Override
    public List<Coach> findByTeam(Team team) {
        return repository.findByTeam(team);
    }

    @Override
    @Transactional
    public void changeTrainingProgram(Long id, Integer programValue) {
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        var coach = userTeam.getCoachById(id);
        var coachPrograms = Coach.CoachProgram.values();
        coach.setCoachProgram(coachPrograms[programValue]);

        var playerOnTraining = coach.getPlayerOnTraining();
        guessTrainingAble(coach, playerOnTraining);
    }


}
