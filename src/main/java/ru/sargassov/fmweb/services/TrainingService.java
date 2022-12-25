package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.comparators.TrainingPlayersComparators;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.converters.PlayerConverter;
import ru.sargassov.fmweb.dto.player_dtos.PlayerOnTrainingDto;
import ru.sargassov.fmweb.spi.TrainingServiceSpi;

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

    @Transactional
    @Override
    public List<PlayerOnTrainingDto> getAllPlayersOnTrainingByUserTeam(Integer parameter) {
        log.info("TrainingService.getAllPlayersOnTrainingByUserTeam()");
        var userTeam = UserHolder.user.getUserTeam();
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
