package ru.sargassov.fmweb.dto;

import lombok.Data;
import ru.sargassov.fmweb.intermediate_entities.Coach;

import java.math.BigDecimal;


@Data
public class CoachDto {
    private int count;
    private String position;
    private String type;
    private String playerOnTraining;
    private String coachProgram;
    private int playerTrAble;

    public String guessPriceResponce() {
        String response = "Price of " + type + " " +
                position + " coach is ";

        if(type.equals("LOCAL")) response += "1";
        else if(type.equals("PROFESSIONAL")) response += "3";
        else response += "5";

        return response += " mln. $. ";
    }

//    public Position guessPosition(){
//        if(position.equals("GOALKEEPER")) return Position.GOALKEEPER;
//        else if(position.equals("DEFENDER")) return Position.DEFENDER;
//        else if(position.equals("MIDFIELDER")) return Position.MIDFIELDER;
//        else return Position.FORWARD;
//    }

    public Coach.CoachType guessType() {
        if(type.equals("LOCAL")) return Coach.CoachType.LOCAL;
        else if(type.equals("PROFESSIONAL")) return Coach.CoachType.PROFESSIONAL;
        else return Coach.CoachType.WORLD_CLASS;
    }

    public BigDecimal guessPrice() {
        if(type.equals("LOCAL")) return BigDecimal.valueOf(1);
        else if(type.equals("PROFESSIONAL")) return BigDecimal.valueOf(3);
        else return BigDecimal.valueOf(5);
    }
}
