package ru.sargassov.fmweb.intermediate_entities;

import lombok.*;
import ru.sargassov.fmweb.constants.BaseUserEntity;
import ru.sargassov.fmweb.exceptions.MarketException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "market")
@Getter
@Setter
@RequiredArgsConstructor
public class Market extends BaseUserEntity {

    @ManyToOne
    @JoinColumn(name = "id_team")
    private Team team;

    @Enumerated(EnumType.STRING)
    private MarketType marketType;

    @ManyToOne
    @JoinColumn(name = "id_start_day")
    private Day startDate;

    @ManyToOne
    @JoinColumn(name = "id_finish_day")
    private Day finishDate;

    public static Market getMarketByTitle(String type) {
        MarketType marketType =  List.of(MarketType.values()).stream()
                .filter(t -> t.toString().equals(type))
                .findFirst()
                .orElseThrow(() ->
                        new MarketException("Market with title = " + type + " not found!"));

        return new Market(marketType, null, null);
    }

    public Market(MarketType marketType, Day startDate, Day finishDate) {
        this.marketType = marketType;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public MarketType getMarketType() {
        return marketType;
    }

    public String getMarketTypeInString() {
        return marketType.toString();
    }

    @Getter
    public enum MarketType {
        RADIO(BigDecimal.valueOf(1), 1.3),
        TV(BigDecimal.valueOf(3.5), 1.45),
        INTERNET(BigDecimal.valueOf(2.2), 1.25),
        NEWSPAPER(BigDecimal.valueOf(0.5), 1.1);

        final BigDecimal oneWeekCost;
        final double capacityCoeff;

        MarketType (BigDecimal oneWeekCost, double capacityCoeff) {
            this.oneWeekCost = oneWeekCost;
            this.capacityCoeff = capacityCoeff;
        }

        public BigDecimal getOneWeekCost() {
            return oneWeekCost;
        }
    }
}

