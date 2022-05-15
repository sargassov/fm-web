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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CoachService {
    private final UserService userService;
    private final CoachConverter coachConverter;

    public List<CoachDto> getAllCoachFromUserTeam() {
        Team team = userService.getUserTeam();
        return team.getCoaches().stream()
                .map(coachConverter::getCoachDtoFromIntermediateEntity)
                .collect(Collectors.toList());
    }

    public void newCoachPurchaseResponce() {
        Team team = userService.getUserTeam();
        if(team.getCoaches().size() == team.maxValueOfCoaches)
            throw new CoachException("You have 6 coaches from 6. You can't purchase more");
    }

    public PriceResponce getPriceForNewCoach(CoachDto coachDto) {
        return new PriceResponce(coachDto.guessPriceResponce());
    }

    public void createNewCoach(CoachDto coachDto) {
        Team team = userService.getUserTeam();
        Coach coach = coachConverter.getIntermediateEntityFromCoachDto(coachDto);

        newCoachPurchaseResponce();

        if(coach.getPrice().compareTo(team.getWealth()) > 0)
            throw new CoachException("Your club haven't money to add this coach");
        else {
            List<Coach> coaches = team.getCoaches();
            team.setWealth(team.getWealth().subtract(coach.getPrice()));
            if(coaches == null)
                coaches = new ArrayList<>();
            coaches.add(coach);
            coach.setCount(coaches.size() - 1);
        }
    }

    public void changePlayerOnTrain(CoachDto coachDto) {
        Coach coach = userService.getCoachListFromUserTeam().get(coachDto.getCount());
        List<Player> players = userService.getPlayerListFromUserTeam().stream()
                .filter(p -> p.getPosition().equals(coach.getPosition()))
                .sorted(Comparator.comparing(Player::getNumber))
                .collect(Collectors.toList());

        if(coachDto.getPlayerOnTraining().equals("N/N")){
            coach.setPlayerOnTraining(players.get(0));
            return;
        }

        Player p = userService.getPlayerByNameFromUserTeam(coachDto.getPlayerOnTraining());
        int countPlayerInTeam = players.indexOf(p) + 1;
        if(countPlayerInTeam == players.size())
            countPlayerInTeam = 0;
        coach.setPlayerOnTraining(players.get(countPlayerInTeam));
    }

    public void deleteCoachByCount(Integer count) {
        List<Coach> coaches = userService.getCoachListFromUserTeam();
        coaches.remove(coaches.get(count));
    }
}
