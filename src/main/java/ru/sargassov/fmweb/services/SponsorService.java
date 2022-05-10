package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api.SponsorApi;
import ru.sargassov.fmweb.converters.SponsorConverter;
import ru.sargassov.fmweb.intermediate_entites.Sponsor;
import ru.sargassov.fmweb.intermediate_entites.Team;
import ru.sargassov.fmweb.repositories.SponsorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SponsorService {
    private final SponsorRepository sponsorRepository;
    private final SponsorConverter sponsorConverter;
    private final TeamService teamService;
    private final SponsorApi sponsorApi;

    public List<Sponsor> getSponsorsFromApi(){
        return sponsorApi.getSponsorApiList();
    }

    public void loadSponsors(){
        log.info("SponsorService.loadSponsors");
        sponsorApi.setSponsorApiList(sponsorRepository.findAll().stream()
                .map(sponsorConverter::entityToDto).collect(Collectors.toList()));

        getRandomSponsorForAllTeams();
    }

    private void getRandomSponsorForAllTeams() {
        teamService.getTeamListFromApi().forEach(this::fillSponsor);
    }

    private void fillSponsor(Team t) {
        int size = sponsorApi.getSponsorApiList().size();
        int random = (int) (Math.random() * size) + 1;
        Sponsor sponsor = sponsorApi.getSponsorFromApiById(random);

        t.setSponsor(sponsor);
        sponsor.signContractWithClub(t);
    }
    /////////////////////////////////////////////////////////////////////стартовые методы

}
