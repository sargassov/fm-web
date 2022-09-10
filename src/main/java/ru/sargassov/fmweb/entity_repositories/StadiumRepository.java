package ru.sargassov.fmweb.entity_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.entities.StadiumEntity;

@Repository
public interface StadiumRepository extends JpaRepository<StadiumEntity, Long> {
}
