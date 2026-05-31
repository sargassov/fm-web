package ru.sargassov.fmweb.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "stadiums")
@Setter
@Getter
@NoArgsConstructor
public class StadiumEntity extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "full_capacity")
    private Integer fullCapacity;

    @ManyToOne
    @JoinColumn(name = "id_leagues")
    private LeagueEntity leagueEntity;

    @OneToOne
    @JoinColumn(name = "id_cities")
    private CityEntity cityEntity;

    @OneToOne
    @JoinColumn(name = "id_teams")
    private TeamEntity teamEntity;
}
