package ru.sargassov.fmweb.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.intermediate_entities.Bank;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

@Repository
public interface BankIntermediateRepository extends JpaRepository<Bank, Long> {

    List<Bank> findByUser(User user);

    List<Bank> findByTeam(Team team);
}