package ru.sargassov.fmweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Role {
    private String title;
    private int posNumber;
}
