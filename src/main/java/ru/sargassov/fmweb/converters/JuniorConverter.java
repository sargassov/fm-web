package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.PlayerDto;
import ru.sargassov.fmweb.dto.PositionDto;
import ru.sargassov.fmweb.entities.Junior;

import java.util.Random;

@Component
@AllArgsConstructor
public class JuniorConverter {
    private final PlayerPriceSetter playerPriceSetter;
    private final Random random = getRandom();

    public String entityToString(Junior junior){
        return junior.getName();
    }

    @Bean
    public Random getRandom() {
        return new Random();
    }

    public void nameToPlayerDto(PlayerDto pDto) {
        int captainValue = 10;
        int strategyPlaceStarting = -100;
        int youngPlayerBirthYear = 2004;
        int trainingAbleValue = 35;
        String natio = "Rus";

        pDto.setNatio(natio);
        setPositionCraft(pDto);

        pDto.setCaptainAble(random.nextInt(captainValue));
        pDto.setStrategyPlace(strategyPlaceStarting);
        pDto.setBirthYear(youngPlayerBirthYear);
        pDto.setTrainingAble(trainingAbleValue);
        pDto.setPrice(playerPriceSetter.createPrice(pDto));

        System.out.println(pDto.toString());
    }

    private void setPositionCraft(PlayerDto pDto) {
        int gkValue = 10, otherValue = 20, averageCraftValue = 10;
        int bottomCraftValue = 60;
        int craftValue = random.nextInt(averageCraftValue) + bottomCraftValue;

        pDto.setGkAble(random.nextInt(gkValue));
        pDto.setDefAble(random.nextInt(otherValue));
        pDto.setMidAble(random.nextInt(otherValue));
        pDto.setForwAble(random.nextInt(otherValue));

        if(pDto.getPosition().equals(PositionDto.GOALKEEPER)) pDto.setGkAble(craftValue);
        else if(pDto.getPosition().equals(PositionDto.DEFENDER)) pDto.setDefAble(craftValue);
        else if(pDto.getPosition().equals(PositionDto.MIDFIELDER)) pDto.setMidAble(craftValue);
        else pDto.setForwAble(craftValue);
    }
}
