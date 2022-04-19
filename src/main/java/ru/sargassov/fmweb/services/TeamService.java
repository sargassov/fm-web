package ru.sargassov.fmweb.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api.PlacementApi;
import ru.sargassov.fmweb.converters.PlacementConverter;
import ru.sargassov.fmweb.converters.TeamConverter;
import ru.sargassov.fmweb.dto.LeagueDto;
import ru.sargassov.fmweb.dto.PlacementDto;
import ru.sargassov.fmweb.dto.TeamDto;
import ru.sargassov.fmweb.entities.Team;
import ru.sargassov.fmweb.exceptions.LeagueNotFoundException;
import ru.sargassov.fmweb.exceptions.TeamNotFoundException;
import ru.sargassov.fmweb.repositories.TeamRepository;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamConverter teamConverter;
    private final PlayerService playerService;
    private final LeagueDto leagueDto;
    private final PlacementApi placementApi;

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

    public Team findById(Long id){
        return teamRepository.findById(id).orElseThrow(() ->
                new TeamNotFoundException(String.format("Team with id = '%s' not found", id)));
    }
}
