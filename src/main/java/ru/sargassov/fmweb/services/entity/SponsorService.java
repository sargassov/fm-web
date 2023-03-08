package ru.sargassov.fmweb.services.entity;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.SponsorConverter;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.repositories.entity.SponsorRepository;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.spi.intermediate_spi.SponsorIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.TeamIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.entity.SponsorServiceSpi;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SponsorService implements SponsorServiceSpi {
    private final SponsorRepository sponsorRepository;
    private final SponsorConverter sponsorConverter;
    private final TeamIntermediateServiceSpi teamIntermediateService;
    private final SponsorIntermediateServiceSpi sponsorIntermediateService;

    @Override
    @Transactional
    public void loadSponsors(User user){
        log.info("SponsorService.loadSponsors");
        var sponsorEntities = sponsorRepository.findAll();
        var notSavedSponsors = sponsorEntities.stream()
                .map(s -> sponsorConverter.getIntermediateEntityFromEntity(s, user))
                .collect(Collectors.toList());
        sponsorIntermediateService.save(notSavedSponsors);
        getRandomSponsorForAllTeams(user);
    }

    @Override
    @Transactional
    public void getRandomSponsorForAllTeams(User user) {
        int sponsorsQuantity = sponsorIntermediateService.getSponsorsQuantity(user);
        for (var t : teamIntermediateService.findAllByUser(user)) {
            fillSponsor(t, user, sponsorsQuantity);
        }
    }

    @Override
    public void fillSponsor(Team t, User user, int sponsorsQuantity) {
        var random = (long) (Math.random() * sponsorsQuantity) + 1;
        var sponsor = sponsorIntermediateService.findBySponsorEntityIdAndUser(random, user);
        t.setSponsor(sponsor);
        sponsor.signContractWithClub(t);
        teamIntermediateService.save(t);
    }


    /////////////////////////////////////////////////////////////////////стартовые методы

//    @Override
//    @Transactional
//    public List<SponsorDto> gelAllSponsors() {
//        return sponsorApi.getSponsorApiList().stream()
//                .filter(s -> !s.getName().equals("Юндекс"))
//                .map(s -> sponsorConverter.getSponsorDtoFromIntermediateEntity(s))
//                .collect(Collectors.toList());
//    }

//    @Override
//    @Transactional
//    public void changeSponsor(SponsorDto sponsorDto) {
//        Team userTeam = userService.getUserTeam();
//        Sponsor sponsor = userTeam.getSponsor();
//
//        userTeam.setWealth(userTeam.getWealth().subtract(sponsor.getContractBonusWage()));
//        Sponsor newSponsor = sponsorApi.getSponsorByName(sponsorDto.getName());
//        userTeam.setSponsor(newSponsor);
//        userTeam.setWealth(userTeam.getWealth().add(newSponsor.getContractBonusWage()));
//        userTeam.setChangeSponsor(true);
//    }

}
