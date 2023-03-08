package ru.sargassov.fmweb.services.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.constants.StadiumAnalytics;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.dto.text_responses.InformationDto;
import ru.sargassov.fmweb.exceptions.StadiumException;
import ru.sargassov.fmweb.intermediate_entities.Stadium;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.repositories.intermediate_repositories.StadiumIntermediateRepository;
import ru.sargassov.fmweb.spi.intermediate_spi.StadiumIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.TeamIntermediateServiceSpi2;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class StadiumIntermediateService implements StadiumIntermediateServiceSpi {
    private final StadiumIntermediateRepository repository;
    private final TeamIntermediateServiceSpi2 teamIntermediateService;

    @Override
    public List<Stadium> save(List<Stadium> notSavedStadiums) {
        return repository.saveAll(notSavedStadiums);
    }

    @Override
    public Stadium findByStadiumEntityIdAndUser(Long stadiumEntityId, User user) {
        return repository.findByStadiumEntityIdAndUser(stadiumEntityId, user);
    }

    @Override
    public Stadium save(Stadium stadium) {
        return repository.save(stadium);
    }

    @Override
    public void assignTeamsToStadiums(List<Team> teams) {
        var savedStadiums = teams.stream()
                .map(team -> assignCurrentTeamToStadium(team))
                .collect(Collectors.toList());
        save(savedStadiums);
    }

    private Stadium assignCurrentTeamToStadium(Team team) {
        var currentStadium = team.getStadium();
        currentStadium.setTeam(team);
        return  currentStadium;
    }

    @Override
    public List<InformationDto> getInfo() {
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        return StadiumAnalytics.getStadiumInforamtion(userTeam);
    }

    @Override
    @Transactional
    public List<InformationDto> getCurrentStatusInfo() {
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        return StadiumAnalytics.getCurrentStadiumStatus(userTeam);
    }

    @Override
    public List<InformationDto> getTicketCostInfo() {
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        return StadiumAnalytics.getTicketCostInfo(userTeam);
    }

    @Override
    @Transactional
    public void changeTicketCost(InformationDto dto) {
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        var stadium = findByTeam(userTeam);
        var typeTicketCost = stadium.getCostByTypeOfSector(dto.getType());
        var decimalDelta = BigDecimal.valueOf((Integer) dto.getValue());
        var zero = BigDecimal.ZERO;
        var newTypeTicketCost = typeTicketCost.add(decimalDelta);

        if (newTypeTicketCost.compareTo(zero) < 0) {
            log.error("Cost of place on sector can't be less than 0!");
            throw new StadiumException("Cost of place on sector can't be less than 0!");
        }

        stadium.setTicketCostByTypeOfSector(dto.getType(), newTypeTicketCost);
    }

    private Stadium findByTeam(Team team) {
        var stadium = repository.findByTeam(team);
        if (stadium.isEmpty()) {
            throw new StadiumException("Stadium for " + team.getName() + " not found!" );
        }
        return stadium.get();
    }

    @Override
    @Transactional
    public List<InformationDto> getSectorsCapacityInfo() {
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        return StadiumAnalytics.getSectorsCapacityInfo(userTeam);
    }
    @Override
    @Transactional
    public List<InformationDto> getSplitSectorsInfo() {
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        return StadiumAnalytics.getSplitSectorsInfo(userTeam);
    }

    @Override
    @Transactional
    public void changeSectorCapacity(InformationDto dto) {
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        var stadium = userTeam.getStadium();
        var delta = (int) dto.getValue();
        var description = dto.getType();

        if (!stadium.approveExtense(description, delta)) {
            var message = String.format("You can't make extension with %s to %d!",  stadium.getTitle(), delta);
            log.error(message);
            throw new StadiumException(String.format(message));
        }
        stadium.makeExtension(description, delta);
        userTeam.setWealth(userTeam.getWealth().subtract(BigDecimal.valueOf(1)));
    }

    @Override
    public Boolean getShowOtherMarketProgramsCondition() {
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        return userTeam.getMarkets().size() < 5;
    }

    @Override
    public List<InformationDto> getFullCapacityInformation() {
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        return StadiumAnalytics.getFullCapacityInformation(userTeam);
    }
    @Transactional
    @Override
    public void expandTheStadium(Integer delta) {
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        var stadium = userTeam.getStadium();
        if (userTeam.getWealth().compareTo(BigDecimal.ONE) < 0) {
            log.error("Too little money to expand the stadium");
            throw new StadiumException("Too little money to expand the stadium");
        }
        stadium.expand(delta);
    }
}
