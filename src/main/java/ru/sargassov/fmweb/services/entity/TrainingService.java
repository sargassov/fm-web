package ru.sargassov.fmweb.services.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.comparators.TrainingPlayersComparators;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.converters.PlayerConverter;
import ru.sargassov.fmweb.dto.player_dtos.PlayerOnTrainingDto;
import ru.sargassov.fmweb.spi.intermediate_spi.TeamIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.entity.TrainingServiceSpi;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class TrainingService implements TrainingServiceSpi {

    private final PlayerConverter playerConverter;
    private final TrainingPlayersComparators trainingPlayersComparators;
    private final TeamIntermediateServiceSpi teamIntermediateService;

    @Transactional
    @Override
    public List<PlayerOnTrainingDto> getAllPlayersOnTrainingByUserTeam(Integer parameter) {
        log.info("TrainingService.getAllPlayersOnTrainingByUserTeam()");
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        var players = userTeam.getPlayerList();
        var playerOnTrainingDtos = players.stream()
                .map(playerConverter::getPlayerOnTrainingDtoFromPlayer)
                .collect(Collectors.toList());
        var usefulComparator = trainingPlayersComparators.getComparators().get(parameter);
        return playerOnTrainingDtos.stream()
                .sorted(usefulComparator)
                .collect(Collectors.toList());
    }

}
