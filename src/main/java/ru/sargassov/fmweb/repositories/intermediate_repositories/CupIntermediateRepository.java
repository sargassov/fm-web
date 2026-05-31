package ru.sargassov.fmweb.repositories.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sargassov.fmweb.intermediate_entities.Cup;
import ru.sargassov.fmweb.intermediate_entities.League;
import ru.sargassov.fmweb.intermediate_entities.User;

public interface CupIntermediateRepository extends JpaRepository<Cup, Long> {

    @Query("select c from Cup c where c.user = ?1")
    Cup findByUser(User user);
}
