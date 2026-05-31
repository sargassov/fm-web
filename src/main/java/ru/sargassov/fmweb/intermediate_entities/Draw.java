package ru.sargassov.fmweb.intermediate_entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.sargassov.fmweb.constants.BaseUserEntity;

import javax.persistence.*;

@Entity
@Table(name = "draw")
@Getter
@Setter
@RequiredArgsConstructor
public class Draw extends BaseUserEntity {

    @Column(name = "shedule")
    private String match;

    @Column(name = "tour_number")
    private Integer tourNumber;

    @ManyToOne
    @JoinColumn(name = "id_league")
    private League league;

    @ManyToOne
    @JoinColumn(name = "id_cup")
    private Cup cup;
}
