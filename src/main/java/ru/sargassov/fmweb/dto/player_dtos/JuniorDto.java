package ru.sargassov.fmweb.dto.player_dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
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
        if(position.equals("GOALKEEPER")) gkAble = correctPower();
        else if(position.equals("DEFENDER")) defAble = correctPower();
        else if(position.equals("MIDFIELDER")) midAble = correctPower();
        else forwAble = correctPower();
    }
}
