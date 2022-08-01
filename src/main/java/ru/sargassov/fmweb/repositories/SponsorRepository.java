package ru.sargassov.fmweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.entities.SponsorEntity;

@Repository
public interface SponsorRepository extends JpaRepository<SponsorEntity, Long> {
}
