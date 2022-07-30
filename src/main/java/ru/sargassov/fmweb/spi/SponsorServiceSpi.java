package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.dto.SponsorDto;
import ru.sargassov.fmweb.intermediate_entites.Sponsor;
import ru.sargassov.fmweb.intermediate_entites.Team;

import java.util.List;

public interface SponsorServiceSpi {

    List<Sponsor> getSponsorsFromApi();

    void loadSponsors();

    void fillSponsor(Team t);

    void getRandomSponsorForAllTeams();

    List<SponsorDto> gelAllSponsors();

    void changeSponsor(SponsorDto sponsorDto);
}
