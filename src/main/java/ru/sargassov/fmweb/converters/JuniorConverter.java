package ru.sargassov.fmweb.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.dto.player_dtos.JuniorDto;
import ru.sargassov.fmweb.enums.PositionType;
import ru.sargassov.fmweb.intermediate_entities.Junior;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.entities.JuniorEntity;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.services.PlayerPriceSetter;

import java.util.Random;

import static ru.sargassov.fmweb.constants.Constant.DEFAULT_STRATEGY_PLACE;

@Component
@RequiredArgsConstructor
public class JuniorConverter {
    private final PlayerPriceSetter playerPriceSetter;
    private final Random random = getRandom();

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

    public Player setSkillForYoungPlayerIntermediateEntity(Player player, User user) {
        var captainValue = 10;
        var strategyPlaceStarting = -100;

        player.selectYoungPlayerNatio();
        setPositionCraft(player);

        player.setCaptainAble(random.nextInt(captainValue));
        player.setStrategyPlace(strategyPlaceStarting);
        player.selectYoungPlayerBirthYear();
        player.selectYoungPlayerTrainingAble();
        var price = playerPriceSetter.createPrice(new PlayerPriceSetter.ValueContainer(player), user);
        player.setPrice(price);
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
        var position = player.getPosition();

        switch (position) {
            case GOALKEEPER:
                player.setGkAble(craftValue);
                break;
            case DEFENDER:
                player.setDefAble(craftValue);
                break;
            case MIDFIELDER:
                player.setMidAble(craftValue);
                break;
            case FORWARD:
                player.setForwAble(craftValue);
                break;
            default:
                throw new IllegalStateException("Wrong position title");

        }
        player.guessPower();
    }

    public Player intermediatePlayerEntityFromJuniorDto(JuniorDto juniorDto) {
        var p = new Player();
        p.setName(juniorDto.getName());
        p.setGkAble(juniorDto.getGkAble());
        p.setDefAble(juniorDto.getDefAble());
        p.setMidAble(juniorDto.getMidAble());
        p.setForwAble(juniorDto.getForwAble());
        p.setCaptainAble(juniorDto.getCaptainAble());
        p.setBirthYear(juniorDto.getBirthYear());
        var position = PositionType.findByDescription(juniorDto.getPosition());
        p.setPosition(position);
        p.setNatio(juniorDto.getNatio());
        p.setPower(juniorDto.getPower());
        p.setPrice(juniorDto.getPrice());
        p.setStrategyPlace(DEFAULT_STRATEGY_PLACE);
        var userTeam = UserHolder.user.getUserTeam();
        p.setTeam(userTeam);
        p.guessNumber(random.nextInt(99) + 1);
        p.guessTrainigAble();
        p.setTire(0);
        p.setTimeBeforeTreat(0);
        p.setTrainingBalance(0);
        return p;
    }

    public JuniorDto juniorDtoFromJunior(Junior junior) {
        var juniorDto = new JuniorDto();
        juniorDto.setName(junior.getName());
        juniorDto.setYoungPlayerHardSkills();
        juniorDto.juniorDtoInit();
        var price = playerPriceSetter.createPrice(new PlayerPriceSetter.ValueContainer(juniorDto), UserHolder.user);
        juniorDto.setPrice(price);
        juniorDto.setId(junior.getId());
        return juniorDto;
    }
}
