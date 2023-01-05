package ru.sargassov.fmweb.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.intermediate_entities.Cortage;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

@Repository
public interface CortageIntermediateRepository extends JpaRepository<Cortage, Long> {

    List<Cortage> findByUser(User user);
}