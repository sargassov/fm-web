package ru.sargassov.fmweb.intermediate_entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.sargassov.fmweb.constants.BaseUserChallengeEntity;
import ru.sargassov.fmweb.entities.LeagueEntity;

import javax.persistence.*;

@Entity
@Table(name = "cup")
@Getter
@Setter
@RequiredArgsConstructor
public class Cup extends BaseUserChallengeEntity {

    @OneToOne
    @JoinColumn(name = "id_league")
    private League league;
}
