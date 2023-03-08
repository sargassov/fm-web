package ru.sargassov.fmweb.repositories.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.intermediate_entities.Stadium;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.Optional;

@Repository
public interface StadiumIntermediateRepository extends JpaRepository<Stadium, Long> {
    Stadium findByStadiumEntityIdAndUser(Long stadiumEntityId, User user);

    Optional<Stadium> findByTeam(Team userTeam);
}