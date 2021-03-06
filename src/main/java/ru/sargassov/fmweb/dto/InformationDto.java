package ru.sargassov.fmweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class InformationDto {
    private String type;
    private Object value;
}