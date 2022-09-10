package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api_temporary_classes_group.SponsorApi;
import ru.sargassov.fmweb.converters.SponsorConverter;
import ru.sargassov.fmweb.dto.SponsorDto;
import ru.sargassov.fmweb.intermediate_entities.Sponsor;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.entity_repositories.SponsorRepository;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_spi.SponsorIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.TeamIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.SponsorServiceSpi;
import ru.sargassov.fmweb.spi.TeamServiceSpi;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SponsorService implements SponsorServiceSpi {
    private final SponsorRepository sponsorRepository;
    private final SponsorConverter sponsorConverter;
    private final TeamIntermediateServiceSpi teamIntermediateService;
    private final SponsorIntermediateServiceSpi sponsorIntermediateService;
    private final UserService userService;
    private final SponsorApi sponsorApi;

    @Override
    @Transactional
    public List<Sponsor> getSponsorsFromApi(){
        return sponsorApi.getSponsorApiList();
    }

    @Override
    @Transactional
    public void loadSponsors(User user){
        log.info("SponsorService.loadSponsors");
        var sponsorEntities = sponsorRepository.findAll();
        var sponsors = sponsorEntities.stream()
                .map(s -> sponsorConverter.getIntermediateEntityFromEntity(s, user))
                .collect(Collectors.toList());

        getRandomSponsorForAllTeams();
    }

    @Override
    @Transactional
    public void getRandomSponsorForAllTeams() {
        teamIntermediateService.findAll().forEach(this::fillSponsor);
    }

    @Override
    public void fillSponsor(Team t) {
        int sponsorsQuantity = sponsorIntermediateService.getSponsorsQuantity();
        long random = (long) (Math.random() * sponsorsQuantity) + 1;
        var sponsor = sponsorIntermediateService.findById(random);

        t.setSponsor(sponsor);
        sponsor.signContractWithClub(t);
        sponsorIntermediateService.save(sponsor);
    }


    /////////////////////////////////////////////////////////////////////стартовые методы

    @Override
    @Transactional
    public List<SponsorDto> gelAllSponsors() {
        return sponsorApi.getSponsorApiList().stream()
                .filter(s -> !s.getName().equals("Юндекс"))
                .map(s -> sponsorConverter.getSponsorDtoFromIntermediateEntity(s))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void changeSponsor(SponsorDto sponsorDto) {
        Team userTeam = userService.getUserTeam();
        Sponsor sponsor = userTeam.getSponsor();

        userTeam.setWealth(userTeam.getWealth().subtract(sponsor.getContractBonusWage()));
        Sponsor newSponsor = sponsorApi.getSponsorByName(sponsorDto.getName());
        userTeam.setSponsor(newSponsor);
        userTeam.setWealth(userTeam.getWealth().add(newSponsor.getContractBonusWage()));
        userTeam.setChangeSponsor(true);
    }

}
