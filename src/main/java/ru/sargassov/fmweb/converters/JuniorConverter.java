package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.player_dtos.JuniorDto;
import ru.sargassov.fmweb.intermediate_entities.Junior;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Position;
import ru.sargassov.fmweb.entities.JuniorEntity;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_spi.PositionIntermediateServiceSpi;
import ru.sargassov.fmweb.services.PlayerPriceSetter;
import ru.sargassov.fmweb.services.UserService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@Component
@AllArgsConstructor
public class JuniorConverter {
    private final PlayerPriceSetter playerPriceSetter;
    private final PositionIntermediateServiceSpi positionIntermediateService;
    private final PlayerConverter playerConverter;
    private final Random random = getRandom();
    private final UserService userService;

    public Junior getIntermediateEntityFromEntity(JuniorEntity juniorEntity, User user){
        var junuior = new Junior();
        junuior.setName(juniorEntity.getName());
        junuior.setUser(user);
        return junuior;
    }


    @Bean
    public Random getRandom() {
        return new Random();
    }

    public Player setSkillForYoungPlayerIntermediateEntity(Player player) {
        var captainValue = 10;
        var strategyPlaceStarting = -100;

        player.selectYoungPlayerNatio();
        setPositionCraft(player);

        player.setCaptainAble(random.nextInt(captainValue));
        player.setStrategyPlace(strategyPlaceStarting);
        player.selectYoungPlayerBirthYear();
        player.selectYoungPlayerTrainingAble();
        player.setPrice(BigDecimal.valueOf(playerPriceSetter.createPrice(player)).setScale(2, RoundingMode.HALF_UP));
        return player;
    }

    private void setPositionCraft(Player player) {
        var gkValue = 10;
        var otherValue = 20;

        player.setGkAble(random.nextInt(gkValue));
        player.setDefAble(random.nextInt(otherValue));
        player.setMidAble(random.nextInt(otherValue));
        player.setForwAble(random.nextInt(otherValue));

        setHardSkill(player);
    }


    private void setHardSkill(Player player){
        var averageCraftValue = 10;
        var bottomCraftValue = 60;
        var craftValue = random.nextInt(averageCraftValue) + bottomCraftValue;
        var position = player.getPosition().getTitle();

        switch (position) {
            case "Goalkeeper":
                player.setGkAble(craftValue);
                break;
            case "Defender":
                player.setDefAble(craftValue);
                break;
            case "Midfielder":
                player.setMidAble(craftValue);
                break;
            case "Forward":
                player.setForwAble(craftValue);
                break;
            default:
                throw new IllegalStateException("Wrong position title");

        }
        player.guessPower();
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
