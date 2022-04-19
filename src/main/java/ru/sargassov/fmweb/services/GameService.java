package ru.sargassov.fmweb.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api.JuniorPoolApi;
import ru.sargassov.fmweb.dto.LeagueDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {
    private final BankService bankService;
    private final JuniorService juniorService;
    private final LeagueService leagueService;
    private final SponsorService sponsorService;
    private final TeamService teamService;
    private final PlacementService placementService;
    private final JuniorPoolApi juniorPoolApi;
    private LeagueDto leagueDto;


    public Object createNewGame() {
        log.info("GameService.createNewGame");
        leagueDto = leagueService.getRussianLeague();
        leagueDto.setSponsorList(sponsorService.getAllSponsors());
        leagueDto.setBanks(bankService.getAllBanks());
        leagueDto.setTeamList(teamService.getAllTeams());
        juniorService.createYouthPool();
        teamService.fillTeams(leagueDto.getTeamList());
        teamService.juniorRecruitment();
        placementService.createPlacementApi();
        placementService.setPlacementsForAllTeams();
        return leagueDto.getTeamList().stream()
                .map(t -> t.getPlayerList().size());
    }
}
