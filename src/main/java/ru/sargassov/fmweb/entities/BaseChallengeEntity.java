package ru.sargassov.fmweb.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class BaseChallengeEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "team_value")
    private Integer teamValue;
}
