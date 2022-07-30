package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api_temporary_classes_group.SponsorApi;
import ru.sargassov.fmweb.converters.SponsorConverter;
import ru.sargassov.fmweb.dto.SponsorDto;
import ru.sargassov.fmweb.intermediate_entites.Sponsor;
import ru.sargassov.fmweb.intermediate_entites.Team;
import ru.sargassov.fmweb.repositories.SponsorRepository;
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
    private final TeamServiceSpi teamService;
    private final UserService userService;
    private final SponsorApi sponsorApi;

    @Override
    @Transactional
    public List<Sponsor> getSponsorsFromApi(){
        return sponsorApi.getSponsorApiList();
    }

    @Override
    @Transactional
    public void loadSponsors(){
        log.info("SponsorService.loadSponsors");
        sponsorApi.setSponsorApiList(sponsorRepository.findAll().stream()
                .map(sponsorConverter::getIntermediateEntityFromEntity).collect(Collectors.toList()));

        getRandomSponsorForAllTeams();
    }

    @Override
    @Transactional
    public void getRandomSponsorForAllTeams() {
        teamService.getTeamListFromApi().forEach(this::fillSponsor);
    }

    @Override
    @Transactional
    public void fillSponsor(Team t) {
        int size = sponsorApi.getSponsorApiList().size();
        int random = (int) (Math.random() * size) + 1;
        Sponsor sponsor = sponsorApi.getSponsorFromApiById(random);

        t.setSponsor(sponsor);
        sponsor.signContractWithClub(t);
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
