package ru.sargassov.fmweb.intermediate_entities;

import lombok.*;
import ru.sargassov.fmweb.constants.BaseUserEntity;

import javax.persistence.*;

@Entity
@Table(name = "goal")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Goal extends BaseUserEntity {

    @OneToOne
    @JoinColumn(name = "id_player")
    private Player scorePlayer;

    @Column(name = "minute")
    private Integer min;

    @ManyToOne
    @JoinColumn(name = "id_match")
    private Match match;
}
