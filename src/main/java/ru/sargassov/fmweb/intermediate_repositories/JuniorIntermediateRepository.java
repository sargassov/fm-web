package ru.sargassov.fmweb.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.intermediate_entities.Junior;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

@Repository
public interface JuniorIntermediateRepository extends JpaRepository<Junior, Long> {
    List<Junior> findByUser(User user);
}
