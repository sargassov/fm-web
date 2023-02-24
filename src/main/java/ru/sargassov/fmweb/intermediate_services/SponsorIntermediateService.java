package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.constants.TextConstant;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.converters.SponsorConverter;
import ru.sargassov.fmweb.dto.SponsorDto;
import ru.sargassov.fmweb.dto.text_responses.TextResponse;
import ru.sargassov.fmweb.exceptions.MarketException;
import ru.sargassov.fmweb.intermediate_entities.Sponsor;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_repositories.SponsorIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.SponsorIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.TeamIntermediateServiceSpi;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class SponsorIntermediateService implements SponsorIntermediateServiceSpi {

    private SponsorIntermediateRepository repository;
    private TeamIntermediateServiceSpi teamIntermediateService;

    private SponsorConverter sponsorConverter;


    @Override
    public Sponsor save(Sponsor sponsor) {
        return repository.save(sponsor);
    }

    @Override
    public int getSponsorsQuantity(User user) {
        return repository.getSponsorsQuantity(user);
    }

    @Override
    public Sponsor findById(long id) {
        var optSponsor = repository.findById(id);
        if (optSponsor.isPresent()) {
            return optSponsor.get();
        }

        throw new EntityExistsException("Sponsor with id #" + id + " not exists");
    }

    @Override
    public List<Sponsor> save(List<Sponsor> sponsors) {
        return repository.saveAll(sponsors);
    }

    @Override
    public List<Sponsor> findAllByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    public Sponsor findBySponsorEntityIdAndUser(long sponsorEntityId, User user) {
        return repository.findBySponsorEntityIdAndUser(sponsorEntityId, user);
    }

    @Override
    public TextResponse getStartSponsorMessage() {
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        var response = new TextResponse();
        var answer = TextConstant.permissionToChangeSponsor(userTeam.isChangeSponsor());
        response.setResponse(answer);
        return response;
    }

    @Override
    public List<SponsorDto> gelAllSponsors() {
        return repository.findByUser(UserHolder.user)
                .stream()
                .filter(s -> !s.getName().equals("Юндекс"))
                .map(s -> sponsorConverter.getSponsorDtoFromIntermediateEntity(s))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void changeSponsor(SponsorDto sponsorDto) {
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        var sponsor = userTeam.getSponsor();
        userTeam.setWealth(userTeam.getWealth().subtract(sponsor.getContractBonusWage()));

        var newSponsor = findById(sponsorDto.getId());
        userTeam.setSponsor(newSponsor);
        userTeam.setWealth(userTeam.getWealth().add(newSponsor.getContractBonusWage()));
        userTeam.setChangeSponsor(true);
    }

    public Sponsor findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new MarketException("Sponsor with id = " + id + " not found!"));
    }
}
