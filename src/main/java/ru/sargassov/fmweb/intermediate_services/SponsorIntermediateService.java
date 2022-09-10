package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_entities.Sponsor;
import ru.sargassov.fmweb.intermediate_repositories.SponsorIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.SponsorIntermediateServiceSpi;

import javax.persistence.EntityExistsException;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class SponsorIntermediateService implements SponsorIntermediateServiceSpi {
    private SponsorIntermediateRepository repository;
    @Override
    public Sponsor save(Sponsor sponsor) {
        return repository.save(sponsor);
    }

    @Override
    public int getSponsorsQuantity() {
        return repository.getSponsorsQuantity();
    }

    @Override
    public Sponsor findById(long id) {
        var optSponsor = repository.findById(id);
        if (optSponsor.isPresent()) {
            return optSponsor.get();
        }

        throw new EntityExistsException("Sponsor with id #" + id + " not exists");
    }
}
