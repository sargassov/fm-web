package ru.sargassov.fmweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.entities.BankEntity;

@Repository
public interface BankRepository extends JpaRepository<BankEntity, Long> {
}
