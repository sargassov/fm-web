package ru.sargassov.fmweb.intermediate_entites;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Role {
    private String title;
    private int posNumber;
}
