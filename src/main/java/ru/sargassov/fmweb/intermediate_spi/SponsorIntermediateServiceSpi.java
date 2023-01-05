package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.dto.SponsorDto;
import ru.sargassov.fmweb.dto.text_responses.TextResponse;
import ru.sargassov.fmweb.intermediate_entities.Sponsor;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

public interface SponsorIntermediateServiceSpi {
    Sponsor save(Sponsor sponsor);

    int getSponsorsQuantity(User user);

    Sponsor findById(long random);

    List<Sponsor> save(List<Sponsor> notSavedSponsors);

    List<Sponsor> findAllByUser(User user);

    Sponsor findBySponsorEntityIdAndUser(long random, User user);

    TextResponse getStartSponsorMessage();

    List<SponsorDto> gelAllSponsors();

    void changeSponsor(SponsorDto sponsorDto);
}
