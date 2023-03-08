package ru.sargassov.fmweb.repositories.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.intermediate_entities.City;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.Optional;

@Repository
public interface CityIntermediateRepository extends JpaRepository<City, Long> {
    Optional<City> findByName(String name);

    City findByCityEntityIdAndUser(Long cityEntityId, User user);
}