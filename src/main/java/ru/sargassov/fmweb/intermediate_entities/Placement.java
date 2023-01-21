package ru.sargassov.fmweb.intermediate_entities;


import lombok.*;
import ru.sargassov.fmweb.constants.BaseUserEntity;
import ru.sargassov.fmweb.exceptions.PlayerNotFoundException;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "placement")
@Getter
@Setter
@RequiredArgsConstructor
public class Placement extends BaseUserEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "title", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Role> roles;

    @OneToOne
    @JoinColumn(name = "id_team")
    private Team team;

    public Role getRoleByPlayerName(String name) {
        for (var role : roles) {
            var player = role.getPlayer();
            var rolePlayerName = player.getName();
            if (rolePlayerName.equals(name)) {
                return role;
            }
        }
        throw new PlayerNotFoundException("Role with player " + name + " not found in this placement");
    }
}

