package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.player_dtos.PlayerHardSkillDto;
import ru.sargassov.fmweb.enums.PositionType;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class PlayerPriceSetter {

    static class TechPrice {

        private double priceInDouble;

        public TechPrice(double priceInDounble) {
            this.priceInDouble = priceInDounble;
        }
    }

    @Data
    public static class ValueContainer {
        private Integer gkAble;
        private Integer defAble;
        private Integer midAble;
        private Integer forwAble;
        private Integer captainAble;
        private Integer birthYear;

        public ValueContainer(Player p) {
            this.birthYear = p.getBirthYear();
            this.captainAble = p.getCaptainAble();
            this.gkAble = p.getGkAble();
            this.defAble = p.getDefAble();
            this.midAble = p.getMidAble();
            this.forwAble = p.getForwAble();
        }

        public ValueContainer(PlayerHardSkillDto p) {
            this.birthYear = p.getBirthYear();
            this.captainAble = p.getCaptainAble();
            this.gkAble = p.getGkAble();
            this.defAble = p.getDefAble();
            this.midAble = p.getMidAble();
            this.forwAble = p.getForwAble();
        }
    }

    private static final double zeroPrice = 0.0;
    private static final double[] priceCoeff = {0.01, 1.0, 2.5, 7.0, 34.0};
    private static final double[] mltpyCoeff = {0.01, 0.15, 0.45, 2.7, 6.5};
    private static final double[] captainCoeff = {1.1, 1.15, 1.2, 1.25, 1.3};
    private static PositionType[] positions;
    private List<Integer> ables;



    public BigDecimal createPrice(ValueContainer v, User user){
        TechPrice techPrice = new TechPrice(zeroPrice);
        init(v.getGkAble(), v.getDefAble(), v.getMidAble(), v.getForwAble(), user);
        priceDetermination(techPrice);
        captainValue(v.getCaptainAble(), techPrice);
        yearBirthValue(v.getBirthYear(), techPrice);

        return BigDecimal.valueOf(techPrice.priceInDouble).setScale(2, RoundingMode.HALF_UP);
    }

    private void yearBirthValue(int birthYear, TechPrice techPrice) {
        if(birthYear < 1988) techPrice.priceInDouble *= 0.8;
        if(birthYear > 2000) techPrice.priceInDouble *= 1.2;
    }

    private void captainValue(int captainAble, TechPrice techPrice) {

        for (int i = 20, y = 0; i < 70; i += 10, y++) {
            if (captainAble> i && captainAble < i + 11)
                techPrice.priceInDouble *= captainCoeff[y];
        }
        if (captainAble > 70) techPrice.priceInDouble *= 1.35;
    }

    private void priceDetermination(TechPrice techPrice) {

        for (int currentAble : ables) {

            for (int i = 60, y = 0; i <= 100; i += 10, y++) {
                if(currentAble < i && y == 0){
                    techPrice.priceInDouble += (priceCoeff[y] + currentAble * mltpyCoeff[y]);
                    break;
                }
                else if (currentAble < i) {
                    techPrice.priceInDouble += (priceCoeff[y] + (currentAble - (i - 10)) * mltpyCoeff[y]);
                    break;}
            }
        }
    }

    private void init(int gkAble, int defAble, int midAble, int forwAble, User user){
        positions = PositionType.values();
        ables = Arrays.asList(gkAble, defAble, midAble, forwAble);
    }
}
