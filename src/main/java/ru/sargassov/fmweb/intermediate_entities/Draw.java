package ru.sargassov.fmweb.intermediate_entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import ru.sargassov.fmweb.constants.BaseUserEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "draw")
@Getter
@Setter
@RequiredArgsConstructor
public class Draw extends BaseUserEntity {

    @Column(name = "shedule")
    private String match;

    @Column(name = "tour_number")
    private Integer tourNumber;
}
