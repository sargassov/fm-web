package ru.sargassov.fmweb.dto.text_responses;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class InformationDto {
    private String type;
    private Object value;
}