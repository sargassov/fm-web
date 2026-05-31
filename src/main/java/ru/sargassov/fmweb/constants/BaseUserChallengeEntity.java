package ru.sargassov.fmweb.constants;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.criteria.CriteriaBuilder;

@Getter
@Setter
@MappedSuperclass
public class BaseUserChallengeEntity extends BaseUserEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "team_value")
    private Integer teamValue;
}
