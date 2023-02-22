package ru.sargassov.fmweb.dto;

import lombok.Data;
import ru.sargassov.fmweb.intermediate_entities.Coach;

import java.math.BigDecimal;

import static ru.sargassov.fmweb.intermediate_entities.Coach.CoachType.LOCAL;
import static ru.sargassov.fmweb.intermediate_entities.Coach.CoachType.PROFESSIONAL;
import static ru.sargassov.fmweb.intermediate_entities.Coach.CoachType.WORLD_CLASS;


@Data
public class CoachDto {

    private static final String LOCAL_PRICE = "1";
    private static final String PROFESSIONAL_PRICE = "3";
    private static final String WORLD_CLASS_PRICE = "5";
    private static final String UNKNOWN_COACH_TYPE = "Unknown coach type";


    private Long id;
    private String position;
    private String type;
    private String playerOnTraining;
    private String coachProgram;
    private int playerTrAble;

    public String guessPriceResponce() {
        String response = "Price of " + type + " " +
                position + " coach is ";

        if (type.equals(LOCAL.getDescription())) {
            response += LOCAL_PRICE;
        } else if (type.equals(PROFESSIONAL.getDescription())) {
            response += PROFESSIONAL_PRICE;
        } else if (type.equals(WORLD_CLASS.getDescription())) {
            response += WORLD_CLASS_PRICE;
        } else {
            throw new IllegalStateException(UNKNOWN_COACH_TYPE);
        }

        return response += " mln. $. ";
    }

    public Coach.CoachType guessType() {
        for (var value : Coach.CoachType.values()) {
            var valueDescription = value.getDescription();
            if (valueDescription.equals(type)) {
                return value;
            }
        }
        throw new IllegalStateException(UNKNOWN_COACH_TYPE);
    }

    public BigDecimal guessPrice() {
        for (var value : Coach.CoachType.values()) {
            var valueDescription = value.getDescription();
            if (valueDescription.equals(type)) {
                return value.getPrice();
            }
        }
        throw new IllegalStateException(UNKNOWN_COACH_TYPE);
    }
}
