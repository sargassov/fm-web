package ru.sargassov.fmweb.intermediate_entities;

import lombok.*;
import ru.sargassov.fmweb.constants.BaseUserEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "coach")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Coach extends BaseUserEntity {

    @Column(name = "count")
    private int count;

    @ManyToOne
    @JoinColumn(name = "id_team")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "id_position")
    private Position position;

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

    public enum CoachProgram {
        STANDART(1),
        INTENSIVE(1.5),
        HARD(2);

        private final double programCode;

        CoachProgram(double programCode){
            this.programCode = programCode;
        }
    }

    public enum CoachType {
        LOCAL(1),
        PROFESSIONAL(1.5),
        WORLD_CLASS(2);

        private final double typeCode;

        CoachType(double typeCode){
            this.typeCode = typeCode;
        }

        public double getTypeCode() {
            return typeCode;
        }
    }

    public double getTrainingCoeff() {
        return this.coachProgram.programCode * this.type.getTypeCode();
    }

}
