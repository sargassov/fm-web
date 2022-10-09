package ru.sargassov.fmweb.intermediate_entities;

import lombok.*;
import ru.sargassov.fmweb.constants.BaseUserEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "city")
@Getter
@Setter
@RequiredArgsConstructor
public class City extends BaseUserEntity {

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_league")
    private League league;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<Team> teams;

    @Column(name = "id_city_entity")
    private Long cityEntityId;

    public List<Team> getTeams() {
        if (teams == null) {
            teams = new ArrayList<>();
        }
        return teams;
    }
}