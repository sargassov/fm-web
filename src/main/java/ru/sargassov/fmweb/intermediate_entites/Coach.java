package ru.sargassov.fmweb.intermediate_entites;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sargassov.fmweb.dto.PriceResponce;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Coach {
    private int count;
    private Position position;
    private CoachType type;
    private CoachProgram coachProgram;
    private Player playerOnTraining;
    private BigDecimal price;

    public enum CoachProgram {
        STANDART,
        INTENSIVE,
        HARD
    }

    public enum CoachType {
        LOCAL,
        PROFESSIONAL,
        WORLD_CLASS
    }

}
