package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api.TeamApi;
import ru.sargassov.fmweb.converters.TeamConverter;
import ru.sargassov.fmweb.dto.*;
import ru.sargassov.fmweb.repositories.TeamRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamConverter teamConverter;
    private final PlayerService playerService;
    private final JuniorService juniorService;
    private final TeamApi teamApi;

    public void loadTeams(){
        teamApi.setTeamApiList(findAll());
        fillTeams(teamApi.getTeamApiList());
        juniorRecruitment(teamApi.getTeamApiList());
    }

    public List<Team> findAll(){
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

    public void fillTeams(List<Team> teamList) {
        log.info("TeamService.fillTeams");
        teamList.forEach(t -> {
            t.setPlayerList(playerService.getAllPlayersByTeamId(t.getId()));
        });
    }

    public void juniorRecruitment(List<Team> teamList) {
        log.info("TeamService.juniorRecruitment");
        int maxValueOfYoungPlayersForOnePosition = 2;

        for(Team currentTeam : teamList){
            for(Position currentPosition : Position.values()){
                for (int i = 0; i < maxValueOfYoungPlayersForOnePosition; i++) {
                    Player player = juniorService.getYoungPlayer(currentPosition);
                    player.setTeam(currentTeam);
                    currentTeam.getPlayerList().add(player);
                }
            }
        }
    }

    public List<Team> getTeamListFromApi() {
        return teamApi.getTeamApiList();
    }

    public Team getTeamByIdFromApi(Long id){
        return teamApi.findTeamById(id);
    }
}
