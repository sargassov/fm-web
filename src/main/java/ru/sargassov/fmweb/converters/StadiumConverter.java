package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.intermediate_entities.League;
import ru.sargassov.fmweb.intermediate_entities.Stadium;
import ru.sargassov.fmweb.entities.StadiumEntity;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_spi.CityIntermediateServiceSpi;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class StadiumConverter {
    private final CityIntermediateServiceSpi cityIntermediateService;
    public Stadium getIntermediateEntityFromEntity(StadiumEntity enitity, User user, League league) {
        var cityEntityId = enitity.getCityEntity().getId();
        Stadium stadium = new Stadium();
        stadium.setUser(user);
        stadium.setStadiumEntityId(enitity.getId());
        stadium.setTitle(enitity.getTitle());
        stadium.setLeague(league);
        stadium.setFullCapacity(enitity.getFullCapacity());
        stadium.setUsualAverageCapacity((int) (stadium.getFullCapacity() * 0.3));
        stadium.setSimpleCapacity(stadium.getUsualAverageCapacity());
        stadium.setSimpleTicketCost(BigDecimal.valueOf(0.00004));
        stadium.setFamilyTicketCost(BigDecimal.ZERO);
        stadium.setFanTicketCost(BigDecimal.ZERO);
        stadium.setAwayTicketCost(BigDecimal.ZERO);
        stadium.setVipTicketCost(BigDecimal.ZERO);
        var city = cityIntermediateService.findByCityEntityIdAndUser(cityEntityId, user);
        stadium.setCity(city);
        return stadium;
    }
}
