package ru.sargassov.fmweb.converters;

import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.Sponsor;
import ru.sargassov.fmweb.entities.SponsorEntity;

@Component
public class SponsorConverter {
    public Sponsor entityToDto(SponsorEntity sponsorEntity){
        Sponsor sDto = new Sponsor();
        sDto.setId(sponsorEntity.getId());
        sDto.setName(sponsorEntity.getName());
        sDto.setDayWage(sponsorEntity.getDayWage());
        sDto.setMatchWage(sponsorEntity.getMatchWage());
        sDto.setGoalBonusWage(sponsorEntity.getGoalBonusWage());
        sDto.setWinWage(sponsorEntity.getWinWage());
        sDto.setDeuceWage(sponsorEntity.getDeuceWage());
        sDto.setContractBonusWage(sponsorEntity.getContractBonusWage());
        return sDto;
    }
}
