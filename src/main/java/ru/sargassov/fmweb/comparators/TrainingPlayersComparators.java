package ru.sargassov.fmweb.comparators;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.player_dtos.PlayerOnTrainingDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
@Getter
public class TrainingPlayersComparators {
    private final List<Comparator<PlayerOnTrainingDto>> comparators = new ArrayList<>(List.of(
            Comparator.comparing(PlayerOnTrainingDto::getName),
            Comparator.comparing(PlayerOnTrainingDto::getPosition),
            Comparator.comparing(PlayerOnTrainingDto::getOnTraining, Comparator.reverseOrder()),
            Comparator.comparing(PlayerOnTrainingDto::getTrainingAble, Comparator.reverseOrder()),
            Comparator.comparing(PlayerOnTrainingDto::getTrainingBalance, Comparator.reverseOrder()),
            Comparator.comparing(PlayerOnTrainingDto::getTire, Comparator.reverseOrder())
    ));
}
