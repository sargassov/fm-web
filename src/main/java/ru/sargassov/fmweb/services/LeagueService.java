package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.converters.CortageConverter;
import ru.sargassov.fmweb.converters.LeagueConverter;
import ru.sargassov.fmweb.dto.LeagueDto;
import ru.sargassov.fmweb.dto.matrix_dto.CortageDto;
import ru.sargassov.fmweb.dto.team_dtos.TeamResultDto;
import ru.sargassov.fmweb.exceptions.LeagueNotFoundException;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.entity_repositories.LeagueRepository;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_spi.LeagueIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.TeamIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.LeagueServiceSpi;
import ru.sargassov.fmweb.spi.MatrixServiceSpi;

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
    private final LeagueIntermediateServiceSpi leagueIntermediateService;
    private final LeagueConverter leagueConverter;
    private final CortageConverter cortageConverter;
    private final MatrixServiceSpi matrixService;
    private final TeamIntermediateServiceSpi teamIntermediateService;
    private static final long RussianLeagueNumber = 1;

    @Override
    @Transactional
    public void getRussianLeague(User user){
        log.info("LeagueService.getRussianLeague");
        var leagueEntity = leagueRepository.findById(RussianLeagueNumber)
                .orElseThrow(() -> new LeagueNotFoundException(String.format("League '%s' not found", "RussianLeagueNumber")));
        var newLeague = leagueConverter.getIntermediateEntityFromEntity(leagueEntity, user);
        leagueIntermediateService.save(newLeague);
    }

    @Override
    @Transactional
    public LeagueDto getLeagueName() {
        return leagueConverter.getLeagueDtoFromIntermediateEntity();
    }

    @Override
    @Transactional
    public List<TeamResultDto> loadTeamTable() {
        var dtoList = new ArrayList<TeamResultDto>();
        var teamList = teamIntermediateService.findAllByUser(UserHolder.user);
        for (var team : teamList) {
            team.calculateTeamPoints();
        }
        var sortedTeamList = getSortedTeamList(teamList);

        var counter = 1;
        for (var team : sortedTeamList) {
            dtoList.add(new TeamResultDto(
                    "" + (counter++) + ".",
                    "" + team.getName(),
                    "" + team.getGames(),
                    "" + team.getWon(),
                    "" + team.getDrawn(),
                    "" + team.getLost(),
                    "" + team.getScored(),
                    "" + team.getMissed(),
                    "" + team.getPoints())
            );
        }
        return dtoList;
    }

    private List<Team> getSortedTeamList(List<Team> teamList) {
        var sortedByPoints = teamList.stream()
                .sorted(Comparator.comparing(Team::getPoints, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        var additionalSortedByWons = new ArrayList<Team>();
        var levelPoints = sortedByPoints.get(0).getPoints();
        while (additionalSortedByWons.size() < teamList.size()) {
            final var finalLevelPoints = levelPoints;
            var levelPointsTeams = sortedByPoints.stream().filter(t -> t.getWon() == finalLevelPoints).collect(Collectors.toList());
            if (levelPointsTeams.isEmpty()) {
                levelPoints -= 1;
                continue;
            }
            var levelPointsTeamsSortedByWons = levelPointsTeams.stream()
                    .sorted(Comparator.comparing(Team::getWon))
                    .collect(Collectors.toList());
            additionalSortedByWons.addAll(levelPointsTeamsSortedByWons);
            levelPoints -= 1;
        }
        return additionalSortedByWons;
    }

    @Override
    public List<CortageDto> loadResultMatrix() {
        var matrix = matrixService.getActualMatrix();
        var dtos = matrix.stream()
                .map(cortageConverter::getCortageDtoFromCortage)
                .collect(Collectors.toList());
        return dtos;
    }
}
