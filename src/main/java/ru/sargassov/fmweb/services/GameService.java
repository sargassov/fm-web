package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.dto.UserData;
import ru.sargassov.fmweb.intermediate_spi.HeadCoachIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.*;

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
    private final HeadCoachIntermediateServiceSpi headCoachIntermediateService;
    private final CheatServiceSpi cheatService;

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
}
