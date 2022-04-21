package ru.sargassov.fmweb.entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "players")
@Data
@NoArgsConstructor
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "natio")
    private String natio;

    @Column(name = "gk_able")
    private Integer gkAble;

    @Column(name = "def_able")
    private Integer defAble;

    @Column(name = "mid_able")
    private Integer midAble;

    @Column(name = "forw_able")
    private Integer forwAble;

    @Column(name = "price")
    private Double price;

    @Column(name = "captain_able")
    private Integer captainAble;

    @Column(name = "number")
    private Integer number;

    @Column(name = "strategy_place")
    private Integer strategyPlace;

    @Column(name = "year")
    private Integer birthYear;

    @OneToOne
    @JoinColumn(name = "id_positions")
    private PositionEntity positionEntity;

    @ManyToOne
    @JoinColumn(name = "id_teams")
    private TeamEntity teamEntity;
}

