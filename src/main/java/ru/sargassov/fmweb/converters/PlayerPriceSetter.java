package ru.sargassov.fmweb.converters;

import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.CreatedPlayerDto;
import ru.sargassov.fmweb.intermediate_entites.Player;
import ru.sargassov.fmweb.intermediate_entites.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PlayerPriceSetter {

    static class TechPrice{

        private double priceInDouble;

        public TechPrice(double priceInDounble) {
            this.priceInDouble = priceInDounble;
        }
    }

    private static final double zeroPrice = 0.0;
    private static final double[] priceCoeff = {0.01, 1.0, 2.5, 7.0, 34.0};
    private static final double[] mltpyCoeff = {0.01, 0.15, 0.45, 2.7, 6.5};
    private static final double[] captainCoeff = {1.1, 1.15, 1.2, 1.25, 1.3};
    private static List<Position> positions;
    private List<Integer> ables;

    public PlayerPriceSetter() {
        positions = new ArrayList<>(List.of(
                Position.GOALKEEPER,
                Position.DEFENDER,
                Position.MIDFIELDER,
                Position.FORWARD
        ));
    }

    public double createPrice(Player player){

        TechPrice techPrice = new TechPrice(zeroPrice);

        init(player);
        priceDetermination(techPrice);
        captainValue(player, techPrice);
        yearBirthValue(player, techPrice);

        return techPrice.priceInDouble;
    }

    private void yearBirthValue(Player player, TechPrice techPrice) {
        if(player.getBirthYear() < 1988) techPrice.priceInDouble *= 0.8;
        if(player.getBirthYear() > 2000) techPrice.priceInDouble *= 1.2;
    }

    private void captainValue(Player player, TechPrice techPrice) {

        for (int i = 20, y = 0; i < 70; i += 10, y++) {
            if (player.getCaptainAble() > i && player.getCaptainAble() < i + 11)
                techPrice.priceInDouble *= captainCoeff[y];
        }
        if (player.getCaptainAble() > 70) techPrice.priceInDouble *= 1.35;
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

    private void init(Player player){
        ables = Arrays.asList(player.getGkAble(),
                player.getDefAble(),
                player.getMidAble(),
                player.getForwAble());
    }
}
