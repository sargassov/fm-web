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
    public Stadium getIntermediateEntityFromEntity(StadiumEntity entity, User user, League league) {
        var cityEntityId = entity.getCityEntity().getId();
        Stadium stadium = new Stadium();
        stadium.setUser(user);
        stadium.setStadiumEntityId(entity.getId());
        stadium.setTitle(entity.getTitle());
        stadium.setLeague(league);
        stadium.setFullCapacity(entity.getFullCapacity());
        stadium.setUsualAverageCapacity((int) (stadium.getFullCapacity() * 0.3));
        stadium.setSimpleCapacity(stadium.getUsualAverageCapacity());

        stadium.setSimpleTicketCost(new BigDecimal(40));
        stadium.setFamilyTicketCost(BigDecimal.ZERO);
        stadium.setFanTicketCost(BigDecimal.ZERO);
        stadium.setAwayTicketCost(BigDecimal.ZERO);
        stadium.setVipTicketCost(BigDecimal.ZERO);
        var city = cityIntermediateService.findByCityEntityIdAndUser(cityEntityId, user);
        stadium.setCity(city);
        return stadium;
    }
}
