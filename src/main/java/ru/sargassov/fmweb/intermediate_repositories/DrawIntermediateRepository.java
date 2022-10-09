package ru.sargassov.fmweb.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.intermediate_entities.Draw;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

@Repository
public interface DrawIntermediateRepository extends JpaRepository<Draw, Long> {
    List<Draw> findByUser(User user);
}
