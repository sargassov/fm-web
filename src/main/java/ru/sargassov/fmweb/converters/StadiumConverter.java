package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.intermediate_entities.League;
import ru.sargassov.fmweb.intermediate_entities.Stadium;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.entities.StadiumEntity;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_spi.LeagueIntermediateServiceSpi;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class StadiumConverter {
    private final LeagueIntermediateServiceSpi leagueIntermediateServiceSpi;
    private final CityConverter cityConverter;

    public Stadium getIntermediateEntityFromEntity(StadiumEntity stadiumEntity, Team team, User user, League league){
        Stadium stadium = new Stadium();
        var city = cityConverter.getIntermediateEntityFromEntity(stadiumEntity.getCityEntity(), team, user, league);

        stadium.setTitle(stadiumEntity.getTitle());
        stadium.setLeague(league);
        stadium.setFullCapacity(stadiumEntity.getFullCapacity());
        stadium.setCity(city);
        stadium.setTeam(team);
        stadium.setUsualAverageCapacity((int) (stadium.getFullCapacity() * 0.3));
        stadium.setSimpleCapacity(stadium.getUsualAverageCapacity());
        stadium.setSimpleTicketCost(BigDecimal.valueOf(0.00004));
        stadium.setFamilyTicketCost(BigDecimal.ZERO);
        stadium.setFanTicketCost(BigDecimal.ZERO);
        stadium.setAwayTicketCost(BigDecimal.ZERO);
        stadium.setVipTicketCost(BigDecimal.ZERO);
        return stadium;
    }
}
