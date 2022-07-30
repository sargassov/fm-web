package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.LeagueConverter;
import ru.sargassov.fmweb.dto.LeagueDto;
import ru.sargassov.fmweb.dto.team_dtos.TeamResultDto;
import ru.sargassov.fmweb.intermediate_entites.League;
import ru.sargassov.fmweb.exceptions.LeagueNotFoundException;
import ru.sargassov.fmweb.intermediate_entites.Team;
import ru.sargassov.fmweb.repositories.LeagueRepository;
import ru.sargassov.fmweb.spi.LeagueServiceSpi;
import ru.sargassov.fmweb.spi.TeamServiceSpi;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class LeagueService implements LeagueServiceSpi {
    private final LeagueRepository leagueRepository;
    private final LeagueConverter leagueConverter;

    private final TeamServiceSpi teamService;
    private static final long RussianLeagueNumber = 1;

    @Override
    @Transactional
    public League getRussianLeague(){
        log.info("LeagueService.getRussianLeague");
        return leagueConverter.getIntermediateEntityFromEntity(leagueRepository.findById(RussianLeagueNumber)
                .orElseThrow(() -> new LeagueNotFoundException(String.format("League '%s' not found", "RussianLeagueNumber"))));
    }

    @Override
    @Transactional
    public LeagueDto getLeagueName() {
        return leagueConverter.getLeagueDtoFromIntermediateEntity();
    }

    @Override
    @Transactional
    public List<TeamResultDto> loadTeamTable() {
        List<TeamResultDto> dtoList = new ArrayList<>();
        List<Team> teamList = teamService.findAll();

        for (Team team : teamList) {
            team.calculateTeamPoints();
        }
        teamList = teamList.stream().sorted(new Comparator<Team>() {
                    @Override
                    public int compare(Team o1, Team o2) {
                        return Integer.compare(o1.getPoints(), o2.getPoints());
                    }
                }).collect(Collectors.toList());

        int counter = 1;
        for(Team team : teamList) {
            team.calculateTeamPoints();
            dtoList.add(new TeamResultDto(
                    "" + (counter++) + ".",
                    "" + team.getName(),
                    "" + team.getGames(),
                    "" + team.getWon(),
                    "" + team.getDrawn(),
                    "" + team.getLost(),
                    "" + team.getScored(),
                    "" + team.getMissed(),
                    "" + team.calculateTeamPoints())
            );
        }
        return dtoList;
    }
}
