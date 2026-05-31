package ru.sargassov.fmweb.repositories.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.entities.DrawEntity;

import java.util.List;

@Repository
public interface DrawRepository extends JpaRepository<DrawEntity, Long> {

    @Query(value = """
           SELECT d.draw FROM draws d WHERE id = (SELECT FLOOR(RANDOM() * (30 - 1 + 1) + 1))
           """, nativeQuery = true)
    String findCupRandomFirstTour();
}
