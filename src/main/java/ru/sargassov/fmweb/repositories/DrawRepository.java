package ru.sargassov.fmweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.entities.DrawEntity;

@Repository
public interface DrawRepository extends JpaRepository<DrawEntity, Long> {
}
