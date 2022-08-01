package ru.sargassov.fmweb.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "days")
@Data
@NoArgsConstructor
public class DayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "day")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    @Column(name = "is_passed")
    private boolean isPassed;

    @Column(name = "is_present")
    private boolean isPresent;

    @Column(name = "is_match")
    private boolean isMatch;
}