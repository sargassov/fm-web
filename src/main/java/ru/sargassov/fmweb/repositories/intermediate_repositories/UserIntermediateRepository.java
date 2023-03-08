package ru.sargassov.fmweb.repositories.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.Optional;

@Repository
public interface UserIntermediateRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM user_table u WHERE u.name = ?1", nativeQuery = true)
    Optional<User> findByName(String login);
}