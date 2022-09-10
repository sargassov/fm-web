package ru.sargassov.fmweb.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.intermediate_entities.League;
import ru.sargassov.fmweb.intermediate_entities.User;

@Repository
public interface LeagueIntermediateRepository extends JpaRepository<League, Long> {

    @Query("select l from League l where l.user = ?1")
    League findByUser(User user);
}