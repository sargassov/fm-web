package ru.sargassov.fmweb.dto.text_responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StartFinishInformationDto {
    private String type;
    private String startDate;
    private String finishDate;
}
