package ru.sargassov.fmweb.converters;

import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.intermediate_entites.Sponsor;
import ru.sargassov.fmweb.entities.SponsorEntity;

import java.math.BigDecimal;

@Component
public class SponsorConverter {
    public Sponsor entityToDto(SponsorEntity sponsorEntity){
        Sponsor sDto = new Sponsor();
        sDto.setId(sponsorEntity.getId());
        sDto.setName(sponsorEntity.getName());
        sDto.setDayWage(BigDecimal.valueOf(sponsorEntity.getDayWage()));
        sDto.setMatchWage(BigDecimal.valueOf(sponsorEntity.getMatchWage()));
        sDto.setGoalBonusWage(BigDecimal.valueOf(sponsorEntity.getGoalBonusWage()));
        sDto.setWinWage(BigDecimal.valueOf(sponsorEntity.getWinWage()));
        sDto.setDeuceWage(BigDecimal.valueOf(sponsorEntity.getDeuceWage()));
        sDto.setContractBonusWage(BigDecimal.valueOf(sponsorEntity.getContractBonusWage()));
        return sDto;
    }
}
