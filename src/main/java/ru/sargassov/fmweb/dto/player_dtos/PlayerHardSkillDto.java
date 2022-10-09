package ru.sargassov.fmweb.dto.player_dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
public class PlayerHardSkillDto extends PlayerDto{
    private Random random;
    protected Integer gkAble;
    protected Integer defAble;
    protected Integer midAble;
    protected Integer forwAble;
    protected Integer captainAble;
    protected Integer birthYear;
    protected String position;
    protected String natio;

    public void playerRandomHardSkillInit(int randomValue){
        random = new Random();
        guessGkAble(randomValue);
        guessDefAble(randomValue);
        guessMidAble(randomValue);
        guessForwAble(randomValue);
        guessCaptainAble(randomValue);
        guessBirthYear();
        guessPosition();

    }

    private void guessBirthYear() {
        birthYear = random.nextInt(25) + 1980;
    }

    private void guessCaptainAble(int randomValue) {
        captainAble = random.nextInt(randomValue) + 1;
    }

    private void guessPosition(){
        int x = random.nextInt(4);
        if(x == 0) position = "GOALKEEPER";
        else if(x == 1) position = "DEFENDER";
        else if(x == 3) position = "MIDFIELDER";
        else position = "FORWARD";
    }

    private void guessGkAble(int randomValue){
        gkAble = random.nextInt(randomValue) + 1;
    }

    private void guessDefAble(int randomValue){
        defAble = random.nextInt(randomValue) + 1;
    }

    private void guessMidAble(int randomValue){
        midAble = random.nextInt(randomValue) + 1;
    }

    private void guessForwAble(int randomValue){
        forwAble = random.nextInt(randomValue) + 1;
    }
}
