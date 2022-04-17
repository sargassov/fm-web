package ru.sargassov.fmweb.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "stadiums")
@Data
@NoArgsConstructor
public class Stadium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "full_capacity")
    private Integer fullCapacity;

    @ManyToOne
    @JoinColumn(name = "id_leagues")
    private League league;

    @OneToOne
    @JoinColumn(name = "id_cities")
    private City city;

    @OneToOne
    @JoinColumn(name = "id_teams")
    private Team team;
}
