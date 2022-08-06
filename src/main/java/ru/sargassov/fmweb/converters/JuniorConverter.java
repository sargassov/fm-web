package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.player_dtos.JuniorDto;
import ru.sargassov.fmweb.intermediate_entites.Player;
import ru.sargassov.fmweb.intermediate_entites.Position;
import ru.sargassov.fmweb.entities.JuniorEntity;
import ru.sargassov.fmweb.services.PlayerPriceSetter;
import ru.sargassov.fmweb.services.UserService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@Component
@AllArgsConstructor
public class JuniorConverter {
    private final PlayerPriceSetter playerPriceSetter;
    private final PlayerConverter playerConverter;
    private final Random random = getRandom();
    private final UserService userService;

    public String entityToString(JuniorEntity juniorEntity){
        return juniorEntity.getName();
    }


    @Bean
    public Random getRandom() {
        return new Random();
    }

    public void nameToIntermediateEntity(Player player) {
        int captainValue = 10;
        int strategyPlaceStarting = -100;
        int youngPlayerBirthYear = 2004;
        int trainingAbleValue = 35;
        String natio = "Rus";

        player.setNatio(natio);
        setPositionCraft(player);

        player.setCaptainAble(random.nextInt(captainValue));
        player.setStrategyPlace(strategyPlaceStarting);
        player.setBirthYear(youngPlayerBirthYear);
        player.setTrainingAble(trainingAbleValue);
        player.setPrice(BigDecimal.valueOf(playerPriceSetter.createPrice(player)).setScale(2, RoundingMode.HALF_UP));
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

        if(pDto.getPosition().equals(Position.GOALKEEPER))pDto.setGkAble(craftValue);
        else if(pDto.getPosition().equals(Position.DEFENDER))pDto.setDefAble(craftValue);
        else if(pDto.getPosition().equals(Position.MIDFIELDER)) pDto.setMidAble(craftValue);
        else pDto.setForwAble(craftValue);

        pDto.guessPower();
    }

    public JuniorDto nameToJuniorDto(String name){
        JuniorDto jDto = new JuniorDto();
        jDto.setName(name);
        jDto.playerRandomHardSkillInit(15);
        jDto.juniorDtoInit();
        jDto.setPrice(playerConverter.getPriceOfIntermediateEntityFromCreatedDto(jDto));
        return jDto;
    }

    public Player juniorDtoToIntermediateEntityPlayer(JuniorDto juniorDto) {
        Player p = new Player();
        p.setName(juniorDto.getName());
        p.setGkAble(juniorDto.getGkAble());
        p.setDefAble(juniorDto.getDefAble());
        p.setMidAble(juniorDto.getMidAble());
        p.setForwAble(juniorDto.getForwAble());
        p.setCaptainAble(juniorDto.getCaptainAble());
        p.setBirthYear(Player.youngPlayerBirthYear);
        p.guessPosition(juniorDto.getPosition());
        p.setNatio(juniorDto.getNatio());
        p.setPower(juniorDto.getPower());
        p.setPrice(juniorDto.getPrice());
        p.setStrategyPlace(-100);
        p.setTeam(userService.getUserTeam());
        p.guessNumber(random.nextInt(99) + 1);
        p.guessTrainigAble();
        return p;
    }
}
