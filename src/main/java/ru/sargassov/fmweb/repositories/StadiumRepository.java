package ru.sargassov.fmweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.entities.Stadium;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium, Long> {
}
