package ru.sargassov.fmweb.services.entity;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.dto.UserData;
import ru.sargassov.fmweb.spi.entity.BankServiceSpi;
import ru.sargassov.fmweb.spi.entity.CheatServiceSpi;
import ru.sargassov.fmweb.spi.entity.CityServiceSpi;
import ru.sargassov.fmweb.spi.entity.DayServiceSpi;
import ru.sargassov.fmweb.spi.entity.DrawServiceSpi;
import ru.sargassov.fmweb.spi.entity.GameServiceSpi;
import ru.sargassov.fmweb.spi.entity.JuniorServiceSpi;
import ru.sargassov.fmweb.spi.entity.LeagueServiceSpi;
import ru.sargassov.fmweb.spi.entity.MatchServiceSpi;
import ru.sargassov.fmweb.spi.entity.MatrixCreateServiceSpi;
import ru.sargassov.fmweb.spi.entity.PlacementServiceSpi;
import ru.sargassov.fmweb.spi.entity.SponsorServiceSpi;
import ru.sargassov.fmweb.spi.entity.StadiumServiceSpi;
import ru.sargassov.fmweb.spi.entity.TeamServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.UserIntermediateServiceSpi;

@Service
@AllArgsConstructor
@Slf4j
public class GameService implements GameServiceSpi {
    private final LeagueServiceSpi leagueService;
    private final SponsorServiceSpi sponsorService;
    private final BankServiceSpi bankService;
    private final JuniorServiceSpi juniorService;
    private final TeamServiceSpi teamService;
    private final PlacementServiceSpi placementService;
    private final DrawServiceSpi drawService;
    private final DayServiceSpi dayService;
    private final UserService userService;
    private final CityServiceSpi cityService;
    private final StadiumServiceSpi stadiumService;
    private final MatchServiceSpi matchService;
    private final MatrixCreateServiceSpi matrixService;
    private final CheatServiceSpi cheatService;
    private final UserIntermediateServiceSpi userIntermediateService;

    @Override
    public void createNewGame(UserData userData) {
        log.info("GameService.createNewGame");
        var user = userService.constructNewUser(userData);
        leagueService.getRussianLeague(user);
        bankService.loadBanks(user);
        juniorService.loadYouthList(user);
        cityService.loadCities(user);
        stadiumService.loadStadiums(user);
        sponsorService.loadSponsors(user);
        teamService.loadTeams(user);
        placementService.loadPlacements(user);
        drawService.loadShedule(user);
        matchService.loadmatches(user);
        dayService.loadCalendar(user);
        matrixService.createMatrix(user);
        userService.setUserTeam(userData, user);
        cheatService.constructCheats(user);
        UserHolder.user = user;
    }

    @Override
    public void loadGame(UserData userData) {
        var user = userIntermediateService.findByUserData(userData);
        if (user == null) {
            throw new IllegalStateException("User + " + userData.getName() + " does not exist");
        }
        var password = user.getPassword();
        if (!userData.getPassword().equals(password)) {
            throw new IllegalStateException("Password incorrect");
        }
        UserHolder.user = user;
    }
}
