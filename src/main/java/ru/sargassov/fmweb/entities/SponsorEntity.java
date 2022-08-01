package ru.sargassov.fmweb.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "sponsors")
@Data
@NoArgsConstructor
public class SponsorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "day_wage")
    private Double dayWage;

    @Column(name = "match_wage")
    private Double matchWage;

    @Column(name = "goal_bonus_wage")
    private Double goalBonusWage;

    @Column(name = "win_wage")
    private Double winWage;

    @Column(name = "deuce_wage")
    private Double deuceWage;

    @Column(name = "contract_bonus_wage")
    private Double contractBonusWage;
}