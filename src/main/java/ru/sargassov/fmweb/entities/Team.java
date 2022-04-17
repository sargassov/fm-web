package ru.sargassov.fmweb.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "teams")
@Data
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "manager")
    private String manager;

    @Column(name = "team_wealth")
    private Double wealth;

    @ManyToOne
    @JoinColumn(name = "id_leagues")
    private League league;

    @OneToOne
    @JoinColumn(name = "id_stadiums")
    private Stadium stadium;

    @ManyToOne
    @JoinColumn(name = "id_cities")
    private City city;

    @OneToMany(mappedBy = "team")
    private List<Player> playerList;
}