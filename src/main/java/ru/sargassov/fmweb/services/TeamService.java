package ru.sargassov.fmweb.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.TeamConverter;
import ru.sargassov.fmweb.dto.*;
import ru.sargassov.fmweb.repositories.TeamRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamConverter teamConverter;
    private final PlayerService playerService;
    private final JuniorService juniorService;
    private final LeagueDto leagueDto;

    public List<TeamDto> getAllTeams(){
        log.info("TeamService.getAllTeams");
        return teamRepository.findAll().stream()
                .map(teamConverter::entityToDto).collect(Collectors.toList());
    }

//    public TeamDto getTeamInLeagueDtoById(Long id) {
//        log.info("TeamService.getTeamInLeagueDtoById");
//        return leagueDto.getTeamList().stream()
//                .filter(t -> t.getId() == id).findFirst().orElseThrow(()
//                -> new RuntimeException("Team with id = " + id + " wasn't found"));
//    }

    public void fillTeams(List<TeamDto> teamList) {
        log.info("TeamService.fillTeams");
        teamList.forEach(t -> t.setPlayerList(playerService.getAllPlayersByTeamId(t.getId())));
    }

    public void juniorRecruitment() {
        int maxValueOfYoungPlayersForOnePosition = 2;

        for(TeamDto currentTeam : leagueDto.getTeamList()){
            for(PositionDto currentPosition : PositionDto.values()){
                for (int i = 0; i < maxValueOfYoungPlayersForOnePosition; i++) {
                    PlayerDto playerDto = juniorService.getYoungPlayer(currentPosition);
                    playerDto.setTeam(currentTeam);
                    currentTeam.getPlayerList().add(playerDto);
                }
            }
        }
    }
}
