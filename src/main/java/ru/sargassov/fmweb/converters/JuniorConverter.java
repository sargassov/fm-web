package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.intermediate_entites.Player;
import ru.sargassov.fmweb.intermediate_entites.Position;
import ru.sargassov.fmweb.entities.JuniorEntity;

import java.util.Random;

@Component
@AllArgsConstructor
public class JuniorConverter {
    private final PlayerPriceSetter playerPriceSetter;
    private final Random random = getRandom();

    public String entityToString(JuniorEntity juniorEntity){
        return juniorEntity.getName();
    }


    @Bean
    public Random getRandom() {
        return new Random();
    }

    public void nameToPlayerDto(Player pDto) {
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
    }

    private void setPositionCraft(Player pDto) {
        int gkValue = 10, otherValue = 20;

        pDto.setGkAble(random.nextInt(gkValue));
        pDto.setDefAble(random.nextInt(otherValue));
        pDto.setMidAble(random.nextInt(otherValue));
        pDto.setForwAble(random.nextInt(otherValue));

        setHardSkill(pDto);
    }

    private void setHardSkill(Player pDto){
        int averageCraftValue = 10;
        int bottomCraftValue = 60;
        int craftValue = random.nextInt(averageCraftValue) + bottomCraftValue;

        if(pDto.getPosition().equals(Position.GOALKEEPER)){
            pDto.setGkAble(craftValue);
            pDto.setPower(pDto.getGkAble());
        }
        else if(pDto.getPosition().equals(Position.DEFENDER)){
            pDto.setDefAble(craftValue);
            pDto.setPower(pDto.getDefAble());
        }
        else if(pDto.getPosition().equals(Position.MIDFIELDER)) {
            pDto.setMidAble(craftValue);
            pDto.setPower(pDto.getMidAble());
        }
        else {
            pDto.setForwAble(craftValue);
            pDto.setPower(pDto.getForwAble());
        }
    }
}
