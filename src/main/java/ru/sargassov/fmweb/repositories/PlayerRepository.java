package ru.sargassov.fmweb.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.dto.Player;
import ru.sargassov.fmweb.entities.PlayerEntity;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {
    @Query("select p from PlayerEntity p where p.teamEntity.id = ?1")
    List<PlayerEntity> findAllByTeamId(Long id);
}
