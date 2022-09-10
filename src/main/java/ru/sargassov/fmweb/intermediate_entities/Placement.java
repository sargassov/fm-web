package ru.sargassov.fmweb.intermediate_entities;


import lombok.*;
import ru.sargassov.fmweb.constants.BaseUserEntity;
import ru.sargassov.fmweb.entities.StadiumEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "placement")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Placement extends BaseUserEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "title", cascade = CascadeType.ALL)
    @JoinColumn(name = "roles")
    private List<Role> roles;

    @OneToOne
    @JoinColumn(name = "id_team")
    private Team team;
}

