package ru.sargassov.fmweb.intermediate_entites;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sargassov.fmweb.dto.PriceResponce;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Coach {
    private int count;
    private Position position;
    private CoachType type;
    private CoachProgram coachProgram;
    private Player playerOnTraining;
    private BigDecimal price;
    private int trainingAble;

    public enum CoachProgram {
        STANDART(1),
        INTENSIVE(1.5),
        HARD(2);

        private final double programCode;

        CoachProgram(double programCode){
            this.programCode = programCode;
        }

        public double getProgramCode() {
            return programCode;
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

}
