package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.comparators.TeamsPlayersComparators;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.converters.PlayerConverter;
import ru.sargassov.fmweb.dto.player_dtos.PlayerSoftSkillDto;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_repositories.TeamIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.PlayerIntermediateServiceSpi2;
import ru.sargassov.fmweb.intermediate_spi.TeamIntermediateServiceSpi2;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class TeamIntermediateService2 implements TeamIntermediateServiceSpi2 {

    private final TeamIntermediateRepository repository;
    private final PlayerIntermediateServiceSpi2 playerIntermediateService2;
    private final TeamsPlayersComparators teamsPlayersComparators;

    @Override
    public List<PlayerSoftSkillDto> getAllPlayersByUserTeam(@NonNull Integer parameter) {
        log.info("TeamService.getAllPlayersByUserTeam()");
        var userTeam = UserHolder.user.getUserTeam();
        var playerList = playerIntermediateService2.findByTeam(userTeam);
        var playerSoftSkillDtos = PlayerConverter.getPlayerSoftSkillDtoFromPlayer(playerList);
        playerSoftSkillDtos.sort(teamsPlayersComparators.getPlayerSoftSkillDtoComparators().get(parameter));
        return playerSoftSkillDtos;
    }

    @Override
    public Team getById(Long teamId) {
        var optTeam = repository.findById(teamId);
        if (optTeam.isPresent()) {
            return optTeam.get();
        }
        throw new EntityNotFoundException("Team with id #" + teamId + " not found");
    }

    @Override
    public Team save(Team team) {
        return repository.save(team);
    }
}
