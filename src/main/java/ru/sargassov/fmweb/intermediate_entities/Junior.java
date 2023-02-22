package ru.sargassov.fmweb.intermediate_entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.sargassov.fmweb.constants.BaseUserEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "junior")
@Getter
@Setter
@RequiredArgsConstructor
public class Junior extends BaseUserEntity {

    @Column(name = "name")
    private String name;
}
