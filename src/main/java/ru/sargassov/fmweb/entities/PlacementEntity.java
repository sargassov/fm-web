package ru.sargassov.fmweb.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "placements")
@Data
@NoArgsConstructor
public class PlacementEntity extends BaseEntity{

    @Column(name = "description")
    private String name;

    @Column(name = "roles")
    private String roles;
}