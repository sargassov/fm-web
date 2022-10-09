package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_entities.Stadium;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_repositories.StadiumIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.StadiumIntermediateServiceSpi;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class StadiumIntermediateService implements StadiumIntermediateServiceSpi {
    private final StadiumIntermediateRepository repository;
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
}
