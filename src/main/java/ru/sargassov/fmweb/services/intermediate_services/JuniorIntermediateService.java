package ru.sargassov.fmweb.services.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.converters.JuniorConverter;
import ru.sargassov.fmweb.converters.PlayerConverter;
import ru.sargassov.fmweb.dto.player_dtos.JuniorDto;
import ru.sargassov.fmweb.dto.text_responses.TextResponse;
import ru.sargassov.fmweb.enums.PositionType;
import ru.sargassov.fmweb.exceptions.YouthAcademyException;
import ru.sargassov.fmweb.intermediate_entities.Junior;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.repositories.intermediate_repositories.JuniorIntermediateRepository;
import ru.sargassov.fmweb.services.entity.UserService;
import ru.sargassov.fmweb.spi.entity.UserServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.JuniorIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.PlayerIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.TeamIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.UserIntermediateServiceSpi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class JuniorIntermediateService implements JuniorIntermediateServiceSpi {

    private PlayerConverter playerConverter;
    private JuniorConverter juniorConverter;
    private UserIntermediateServiceSpi userIntermediateService;
    private PlayerIntermediateServiceSpi playerIntermediateService;
    private TeamIntermediateServiceSpi teamIntermediateService;

    private JuniorIntermediateRepository repository;
    @Override
    public void save(List<Junior> juniorPlayers) {
        repository.saveAll(juniorPlayers);
    }

    @Override
    public Player getYoungPlayerForPosition(PositionType currentPosition, User user, List<Junior> allJuniors) {
        log.info("JuniorService.getYoungPlayer");
        var random = new Random();
        var allJuniorsSize = allJuniors.size();
        var selected = random.nextInt(allJuniorsSize);
        var junior = getRandomYoungPlayer(selected, allJuniors);
        return playerConverter.getIntermadiateEntityFromJunior(junior, currentPosition, user);
    }

    @Override
    public List<Junior> findByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    public TextResponse isUserVisitedYouthAcademyToday() {
        return UserHolder.user.isYouthAcademyVisited()
                ? new TextResponse("You have already visited youth academy today!")
                : new TextResponse("You can choose one of young players");
    }

    private Junior getRandomYoungPlayer(int selected, List<Junior> allJuniors) {
        var junior = allJuniors.get(selected);
        repository.deleteById(junior.getId());
        allJuniors.remove(junior);
        return junior;
    }

    private void delete(Long id) {
        repository.deleteById(id);
    }
    @Override
    public List<JuniorDto> getRandomFiveYoungPlayers(TextResponse response) {
        var responseText = response.getResponse();
        if (responseText != null && responseText.startsWith("You have already")) {
            return new ArrayList<>();
        }
        var user = UserHolder.user;
        var juniorPoolIds = repository.getJuniorPoolIds(user);
        var juniorSet = new HashSet<Junior>();
        var random = new Random();
        for (;juniorSet.size() < 5;) {
            var randomLong = juniorPoolIds.get(random.nextInt(juniorPoolIds.size()));
            var junior = repository.findByIdAndUser(randomLong, user);
            juniorSet.add(junior);
        }
        return juniorSet.stream()
                .map(j -> juniorConverter.juniorDtoFromJunior(j))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TextResponse invokeYoungPlayerInUserTeam(JuniorDto juniorDto) {
        var user = UserHolder.user;
        var userTeamId = user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        var userTeamWealth = userTeam.getWealth();
        var youngPlayerPrice = juniorDto.getPrice();
        if (userTeamWealth.compareTo(youngPlayerPrice) < 0) {
            try{
                throw new YouthAcademyException("The wealth of User team is less than young player price!");
            } catch (YouthAcademyException y){
                log.error(y.getMessage());
                return new TextResponse(y.getMessage());
            }
        }
        var player = juniorConverter.intermediatePlayerEntityFromJuniorDto(juniorDto);
        player.setTeam(userTeam);
        player.setUser(UserHolder.user);
        player.setTrainingBalance(0);
        player.setTire(0);
        var league = userTeam.getLeague();
        player.setLeague(league);
        var savedPlayer = playerIntermediateService.save(player);
        userTeam.setWealth(userTeamWealth.subtract(savedPlayer.getPrice()));
        userTeam.getPlayerList().add(savedPlayer);
        delete(juniorDto.getId());;
        user.setYouthAcademyVisited(true);
        userIntermediateService.save(user);
        return new TextResponse("Player " + savedPlayer.getName() + " was invoked in Your team");
    }
}
