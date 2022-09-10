package ru.sargassov.fmweb.intermediate_entities;

import lombok.*;
import ru.sargassov.fmweb.constants.BaseUserEntity;

import javax.persistence.*;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Role extends BaseUserEntity {

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "id_team")
    private Team team;

    @OneToOne
    @JoinColumn(name = "id_player")
    private Player player;

    @Column(name = "pos_number")
    private int posNumber;
}
