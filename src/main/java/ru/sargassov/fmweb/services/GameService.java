package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.dto.League;
import ru.sargassov.fmweb.dto.UserData;

@Service
@AllArgsConstructor
@Slf4j
public class GameService {
    private final LeagueService leagueService;
    private final SponsorService sponsorService;
    private final BankService bankService;
    private final JuniorService juniorService;
    private final TeamService teamService;
    private final PlacementService placementService;
    private final DrawService drawService;
    private final DayService dayService;
    private final UserService userService;
    private League league;

    public void createNewGame(UserData userData) {
        log.info("GameService.createNewGame");
        league = leagueService.getRussianLeague();
        bankService.loadBanks();
        juniorService.loadYouthList();
        teamService.loadTeams();
        placementService.loadPlacements();
        sponsorService.loadSponsors();
        drawService.loadShedule();
        dayService.loadCalendar();
        userService.fillUserApi(userData);
    }
}
