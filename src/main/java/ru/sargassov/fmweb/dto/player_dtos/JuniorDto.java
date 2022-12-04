package ru.sargassov.fmweb.dto.player_dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class JuniorDto extends PlayerHardSkillDto{
    private Integer power;
    private BigDecimal price;

    public void juniorDtoInit(){
        setRussianNatio();
        setJuniorDtoPower();
    }

    private void setRussianNatio(){
        natio = "Rus";
    }

    private int correctPower(){
        power = getRandom().nextInt(10) + 60;
        return power;
    }

    private void setJuniorDtoPower(){
        switch (position) {
            case "GOALKEEPER":
                gkAble = correctPower();
                break;
            case "DEFENDER":
                defAble = correctPower();
                break;
            case "MIDFIELDER":
                midAble = correctPower();
                break;
            case "FORWARD":
                forwAble = correctPower();
                break;
            default:
                throw new IllegalStateException("Illegal position");
        }
    }
}
