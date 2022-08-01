package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.CoachConverter;
import ru.sargassov.fmweb.dto.CoachDto;
import ru.sargassov.fmweb.dto.PriceResponce;
import ru.sargassov.fmweb.exceptions.CoachException;
import ru.sargassov.fmweb.intermediate_entites.Coach;
import ru.sargassov.fmweb.intermediate_entites.Player;
import ru.sargassov.fmweb.intermediate_entites.Team;
import ru.sargassov.fmweb.spi.CoachServiceSpi;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CoachService implements CoachServiceSpi {
    private final UserService userService;
    private final CoachConverter coachConverter;

    @Override
    @Transactional
    public List<CoachDto> getAllCoachFromUserTeam() {
        Team team = userService.getUserTeam();
        return team.getCoaches().stream()
                .map(coachConverter::getCoachDtoFromIntermediateEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void newCoachPurchaseResponce() {
        Team team = userService.getUserTeam();
        if(team.getCoaches().size() == team.maxValueOfCoaches)
            throw new CoachException("You have 6 coaches from 6. You can't purchase more");
    }

    @Override
    @Transactional
    public PriceResponce getPriceForNewCoach(CoachDto coachDto) {
        return new PriceResponce(coachDto.guessPriceResponce());
    }

    @Override
    @Transactional
    public void createNewCoach(CoachDto coachDto) {
        Team team = userService.getUserTeam();
        Coach coach = coachConverter.getIntermediateEntityFromCoachDto(coachDto);

        newCoachPurchaseResponce();

        if(coach.getPrice().compareTo(team.getWealth()) > 0)
            throw new CoachException("Your club haven't money to add this coach");
        else {
            List<Coach> coaches = team.getCoaches();
            team.setWealth(team.getWealth().subtract(coach.getPrice()));
            team.substractPersonalExpenses(coach.getPrice());
            if(coaches == null)
                coaches = new ArrayList<>();
            coaches.add(coach);
            coach.setCount(coaches.size() - 1);
        }
    }

    @Override
    @Transactional
    public List<Player> getBusyPlayers(){
       return userService.getCoachListFromUserTeam().stream()
                .map(Coach::getPlayerOnTraining)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<Player> getPlayerComparingByNumberAndPositionOfCoach(Coach coach){
        return userService.getPlayerListFromUserTeam().stream()
                .filter(p -> p.getPosition().equals(coach.getPosition()))
                .sorted(Comparator.comparing(Player::getNumber))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void changePlayerOnTrain(CoachDto coachDto) {
        Coach coach = userService.getCoachListFromUserTeam().get(coachDto.getCount());
        List<Player> players = getPlayerComparingByNumberAndPositionOfCoach(coach);
        Player p;

        coach.setCoachProgram(Coach.CoachProgram.STANDART);
        coach.setTrainingAble(0);

        if(coachDto.getPlayerOnTraining().equals("N/N")){
            int countPlayerInList = 0;
            selectingPlayerOnTrain(players, countPlayerInList, coach);
            return;
        }

        p = userService.getPlayerByNameFromUserTeam(coachDto.getPlayerOnTraining());
        int countPlayerInList = players.indexOf(p);
        selectingPlayerOnTrain(players, countPlayerInList, coach);
    }

    @Override
    @Transactional
    public void selectingPlayerOnTrain(List<Player> players, int countPlayerInList, Coach coach) {
        for(int x = 0; x < players.size(); x++){
            if(getBusyPlayers().contains(players.get(countPlayerInList))){
                countPlayerInList++;
                if(countPlayerInList == players.size()) countPlayerInList = 0;
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
        possibleTrainingAble *= coach.getCoachProgram().getProgramCode();
        possibleTrainingAble *= coach.getType().getTypeCode();
        coach.setTrainingAble((int) (possibleTrainingAble * player.getTrainingAble()));
    }

    @Override
    @Transactional
    public void deleteCoachByCount(Integer count) {
        List<Coach> coaches = userService.getCoachListFromUserTeam();
        coaches.remove(coaches.get(count));
        replaceCounters(coaches);
    }

    @Override
    @Transactional
    public void replaceCounters(List<Coach> coaches) {
        for (int i = 0; i < coaches.size(); i++) {
            Coach coach = coaches.get(i);
            coach.setCount(i);
        }
    }

    @Override
    @Transactional
    public void changeTrainingProgram(Integer count, Integer program) {
        Coach coach = userService.getCoachListFromUserTeam().get(count);
        Coach.CoachProgram[] allPrograms = Coach.CoachProgram.values();
        coach.setCoachProgram(allPrograms[program]);

        Player player = coach.getPlayerOnTraining();
        guessTrainingAble(coach, player);
    }
}
