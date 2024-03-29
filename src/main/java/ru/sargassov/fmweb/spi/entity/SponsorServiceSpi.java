package ru.sargassov.fmweb.spi.entity;

import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

public interface SponsorServiceSpi {

//    List<Sponsor> getSponsorsFromApi();

    void loadSponsors(User user);

    void fillSponsor(Team t, User user, int sponsorsQuantity);

    void getRandomSponsorForAllTeams(User user);

//    List<SponsorDto> gelAllSponsors();

//    void changeSponsor(SponsorDto sponsorDto);
}
