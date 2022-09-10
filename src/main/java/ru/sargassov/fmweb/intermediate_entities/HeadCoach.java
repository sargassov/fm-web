package ru.sargassov.fmweb.intermediate_entities;

import lombok.*;
import ru.sargassov.fmweb.constants.BaseUserEntity;

import javax.persistence.*;

@Entity
@Table(name = "head_coach")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class HeadCoach extends BaseUserEntity {

    @Column(name = "name")
    private String name;
}
