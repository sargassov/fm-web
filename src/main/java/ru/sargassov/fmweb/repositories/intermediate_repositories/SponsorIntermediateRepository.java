package ru.sargassov.fmweb.repositories.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.intermediate_entities.Sponsor;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

@Repository
public interface SponsorIntermediateRepository extends JpaRepository<Sponsor, Long> {

    @Query("select count(s) from Sponsor s where s.user = ?1")
    int getSponsorsQuantity(User user);

    List<Sponsor> findByUser(User user);

    Sponsor findBySponsorEntityIdAndUser(long sponsorEntityId, User user);
}