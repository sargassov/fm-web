package ru.sargassov.fmweb.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.entities.CityEntity;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {
}

