package ru.sargassov.fmweb.intermediate_entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.sargassov.fmweb.constants.IntermediateEntity;

import javax.persistence.*;

@Entity
@Table(name = "position")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Position extends IntermediateEntity {

    @Column(name = "title")
    private String title;

    private long positionEntityId;
}
