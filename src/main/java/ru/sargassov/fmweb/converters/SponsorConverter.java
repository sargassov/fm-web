package ru.sargassov.fmweb.converters;

import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.SponsorDto;
import ru.sargassov.fmweb.intermediate_entities.Sponsor;
import ru.sargassov.fmweb.entities.SponsorEntity;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_spi.SponsorIntermediateServiceSpi;

import java.math.BigDecimal;

@Component
public class SponsorConverter {
    private SponsorIntermediateServiceSpi sponsorIntermediateService;

    public Sponsor getIntermediateEntityFromEntity(SponsorEntity sponsorEntity, User user){
        var sponsor = new Sponsor();
        sponsor.setName(sponsorEntity.getName());
        sponsor.setSponsorEntityId(sponsorEntity.getId());
        sponsor.setUser(user);
        sponsor.setDayWage(BigDecimal.valueOf(sponsorEntity.getDayWage()));
        sponsor.setMatchWage(BigDecimal.valueOf(sponsorEntity.getMatchWage()));
        sponsor.setGoalBonusWage(BigDecimal.valueOf(sponsorEntity.getGoalBonusWage()));
        sponsor.setWinWage(BigDecimal.valueOf(sponsorEntity.getWinWage()));
        sponsor.setDeuceWage(BigDecimal.valueOf(sponsorEntity.getDeuceWage()));
        sponsor.setContractBonusWage(BigDecimal.valueOf(sponsorEntity.getContractBonusWage()));
        return sponsor;
    }

    public SponsorDto getSponsorDtoFromIntermediateEntity(Sponsor s) {
        var sponsorDto = new SponsorDto();
        sponsorDto.setId(s.getId());
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
