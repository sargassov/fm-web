package ru.sargassov.fmweb.intermediate_entities;

import lombok.*;
import ru.sargassov.fmweb.constants.BaseUserEntity;
import ru.sargassov.fmweb.enums.PositionType;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "coach")
@Getter
@Setter
@RequiredArgsConstructor
public class Coach extends BaseUserEntity {

    @ManyToOne
    @JoinColumn(name = "id_team")
    private Team team;

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private PositionType position;

    @Enumerated(EnumType.STRING)
    @Column(name = "coach_type")
    private CoachType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "coach_program")
    private CoachProgram coachProgram;

    @OneToOne
    @JoinColumn(name = "id_player_on_training")
    private Player playerOnTraining;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "training_able")
    private int trainingAble;

    @Getter
    @RequiredArgsConstructor
    public enum CoachProgram {
        STANDART(1, "Standart"),
        INTENSIVE(1.5, "Intensive"),
        HARD(2, "Hard");

        private final double programCode;
        private final String description;
    }

    @Getter
    @RequiredArgsConstructor
    public enum CoachType {
        LOCAL(1, "Local", BigDecimal.valueOf(1)),
        PROFESSIONAL(1.5, "Professional", BigDecimal.valueOf(3)),
        WORLD_CLASS(2, "World Class", BigDecimal.valueOf(5));

        private final double typeCode;
        private final String description;
        private final BigDecimal price;
    }

    public double getTrainingCoeff() {
        return this.coachProgram.programCode * this.type.getTypeCode();
    }

}
