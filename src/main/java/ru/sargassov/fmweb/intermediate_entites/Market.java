package ru.sargassov.fmweb.intermediate_entites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.sargassov.fmweb.exceptions.MarketException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Market {
    private MarketType marketType;
    private LocalDate startDate;
    private LocalDate finishDate;

    public static Market getMarketByTitle(String type) {
        MarketType marketType =  List.of(MarketType.values()).stream()
                .filter(t -> t.toString().equals(type))
                .findFirst()
                .orElseThrow(() ->
                        new MarketException("Market with title = " + type + " not found!"));

        return new Market(marketType, null, null);
    }

    public MarketType getMarketType() {
        return marketType;
    }

    public String getMarketTypeInString() {
        return marketType.toString();
    }

    @Getter
    public enum MarketType {
        RADIO(BigDecimal.valueOf(1)),
        TV(BigDecimal.valueOf(3.5)),
        INTERNET(BigDecimal.valueOf(2.2)),
        NEWSPAPER(BigDecimal.valueOf(0.5));

        BigDecimal oneWeekCost;

        MarketType (BigDecimal oneWeekCost) {
            this.oneWeekCost = oneWeekCost;
        }

        public BigDecimal getOneWeekCost() {
            return oneWeekCost;
        }
    }
}

