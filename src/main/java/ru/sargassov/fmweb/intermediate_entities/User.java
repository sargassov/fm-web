package ru.sargassov.fmweb.intermediate_entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.sargassov.fmweb.constants.IntermediateEntity;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class User extends IntermediateEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "about")
    private String about;

    @OneToOne
    @JoinColumn(name = "id_team")
    private Team userTeam;

    @Column(name = "user_team_description")
    private String userTeamDescription;
}
