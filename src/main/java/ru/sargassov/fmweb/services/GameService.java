package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.dto.UserData;
import ru.sargassov.fmweb.spi.*;

import javax.transaction.Transactional;

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
    private final MatrixCreateServiceSpi matrixService;
    private final CheatServiceSpi cheatService;

    @Override
    @Transactional
    public void createNewGame(UserData userData) {
        log.info("GameService.createNewGame");
        var user = userService.constructNewUser(userData);
        leagueService.getRussianLeague(user);
        bankService.loadBanks(user);
        juniorService.loadYouthList(user);
        teamService.loadTeams(user);
        placementService.loadPlacements(user);
        sponsorService.loadSponsors(user);
        drawService.loadShedule(user);
        dayService.loadCalendar(user);
        matrixService.createMatrix(user);
        userService.setUserTeam(user);
        cheatService.constructCheats(user);
    }
}
