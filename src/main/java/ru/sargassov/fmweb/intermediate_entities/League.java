package ru.sargassov.fmweb.intermediate_entities;

import lombok.*;
import ru.sargassov.fmweb.constants.BaseUserChallengeEntity;

import javax.persistence.*;

@Entity
@Table(name = "league")
@Getter
@Setter
@RequiredArgsConstructor
public class League extends BaseUserChallengeEntity {

}