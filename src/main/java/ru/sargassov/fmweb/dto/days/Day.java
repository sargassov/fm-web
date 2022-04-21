package ru.sargassov.fmweb.dto.days;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
public class Day {
    private Long id;
    private LocalDate date;
    private boolean isPassed;
    private boolean isPresent;
    private boolean isMatch;
}
