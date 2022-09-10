package ru.sargassov.fmweb.intermediate_entities;

import lombok.*;
import ru.sargassov.fmweb.constants.BaseUserEntity;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "city")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class City extends BaseUserEntity {

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_league")
    private League league;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    @JoinColumn(name = "teams")
    private List<Team> teams;
}