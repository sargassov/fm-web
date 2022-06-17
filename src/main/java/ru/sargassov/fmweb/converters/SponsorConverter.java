package ru.sargassov.fmweb.converters;

import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.SponsorDto;
import ru.sargassov.fmweb.intermediate_entites.Sponsor;
import ru.sargassov.fmweb.entities.SponsorEntity;

import java.math.BigDecimal;

@Component
public class SponsorConverter {
    public Sponsor getIntermediateEntityFromEntity(SponsorEntity sponsorEntity){
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

    public SponsorDto getSponsorDtoFromIntermediateEntity(Sponsor s) {
        SponsorDto sponsorDto = new SponsorDto();
        sponsorDto.setName(s.getName());
        sponsorDto.setDayWage(s.getDayWage());
        sponsorDto.setMatchWage(s.getMatchWage());
        sponsorDto.setWinWage(s.getWinWage());
        sponsorDto.setDeuceWage(s.getDeuceWage());
        sponsorDto.setContractBonusWage(s.getContractBonusWage());
        sponsorDto.setGoalBonusWage(s.getGoalBonusWage());
        return sponsorDto;
    }
}
