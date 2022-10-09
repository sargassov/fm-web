package ru.sargassov.fmweb.intermediate_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Role;
import ru.sargassov.fmweb.intermediate_entities.User;

@Repository
public interface RoleIntermediateRepository extends JpaRepository<Role, Long> {

    @Modifying
    @Query("update Role r set r.player = null where r.id = ?1")
    void resetPlayerById(Long roleId);

    Role findByPlayerAndUser(Player player, User user);
}