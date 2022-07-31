package ru.sargassov.fmweb.api_temporary_classes_group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.intermediate_entites.Cortage;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@Getter
public class MatrixApi { //TEMPORARY CLASS
    private List<Cortage> matrix;

    public List<Cortage> getMatrix() {
        return matrix;
    }

    private Cortage getLineByNumber(int num) {
        return matrix.get(num);
    }

    public void setMatrix(List<Cortage> cortages) {
        matrix = cortages;
    }

    public List<Cortage> constructMatrix() {
        matrix = new ArrayList<>();
        return matrix;
    }
}
