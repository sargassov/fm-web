package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.dto.League;

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
    private League league;

    public Object createNewGame() {
        log.info("GameService.createNewGame");
        league = leagueService.getRussianLeague();
        sponsorService.loadSponsors();
        bankService.loadBanks();
        juniorService.loadYouthList();
        teamService.loadTeams();
        placementService.loadPlacements();
        drawService.loadShedule();
        dayService.loadCalendar();
        return dayService.getCalendarFromApi().stream().map(d -> d.getDate().getMonth());
    }
}
