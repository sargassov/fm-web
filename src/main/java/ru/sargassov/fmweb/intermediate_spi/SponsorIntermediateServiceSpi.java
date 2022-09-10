package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.Sponsor;

public interface SponsorIntermediateServiceSpi {
    Sponsor save(Sponsor sponsor);

    int getSponsorsQuantity();

    Sponsor findById(long random);
}
