package ru.sargassov.fmweb.intermediate_entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.sargassov.fmweb.constants.BaseUserEntity;
import ru.sargassov.fmweb.constants.IntermediateEntity;

import javax.persistence.*;

@Entity
@Table(name = "position")
@Getter
@Setter
public class Position extends BaseUserEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "id_position_Entity")
    private Long positionEntityId;

    @Override
    public String toString() {
        return title;
    }
}
