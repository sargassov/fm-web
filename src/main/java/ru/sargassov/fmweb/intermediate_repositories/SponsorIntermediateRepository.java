package ru.sargassov.fmweb.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.intermediate_entities.Sponsor;

@Repository
public interface SponsorIntermediateRepository extends JpaRepository<Sponsor, Long> {

    @Query("select count(s) from Sponsor s")
    int getSponsorsQuantity();
}