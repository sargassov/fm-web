package ru.sargassov.fmweb.converters;

import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.SponsorDto;
import ru.sargassov.fmweb.entities.Sponsor;

@Component
public class SponsorConverter {
    public SponsorDto entityToDto(Sponsor sponsor){
        SponsorDto sDto = new SponsorDto();
        sDto.setId(sponsor.getId());
        sDto.setName(sponsor.getName());
        sDto.setDayWage(sponsor.getDayWage());
        sDto.setMatchWage(sponsor.getMatchWage());
        sDto.setGoalBonusWage(sponsor.getGoalBonusWage());
        sDto.setWinWage(sponsor.getWinWage());
        sDto.setDeuceWage(sponsor.getDeuceWage());
        sDto.setContractBonusWage(sponsor.getContractBonusWage());
        return sDto;
    }
}
