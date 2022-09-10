package ru.sargassov.fmweb.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.intermediate_entities.Market;

@Repository
public interface MarketIntermediateRepository extends JpaRepository<Market, Long> {
}