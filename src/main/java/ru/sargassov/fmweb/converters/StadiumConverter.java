package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.intermediate_entites.League;
import ru.sargassov.fmweb.intermediate_entites.Stadium;
import ru.sargassov.fmweb.intermediate_entites.Team;
import ru.sargassov.fmweb.entities.StadiumEntity;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class StadiumConverter {
    private final League league;
    private final CityConverter cityConverter;

    public Stadium getIntermediateEntityFromEntity(StadiumEntity stadiumEntity, Team tDto){
        Stadium sDto = new Stadium();
        sDto.setId(stadiumEntity.getId());
        sDto.setTitle(stadiumEntity.getTitle());
        sDto.setLeague(league);
        sDto.setFullCapacity(stadiumEntity.getFullCapacity());
        sDto.setCity(cityConverter.entityToDto(stadiumEntity.getCityEntity()));
        sDto.setTeam(tDto);
        sDto.setUsualAverageCapacity((int) (sDto.getFullCapacity() * 0.3));
        sDto.setSimpleCapacity(sDto.getUsualAverageCapacity());
        sDto.setSimpleTicketCost(BigDecimal.valueOf(0.00004));
        sDto.setFamilyTicketCost(BigDecimal.ZERO);
        sDto.setFanTicketCost(BigDecimal.ZERO);
        sDto.setAwayTicketCost(BigDecimal.ZERO);
        sDto.setVipTicketCost(BigDecimal.ZERO);
        return sDto;
    }
}
