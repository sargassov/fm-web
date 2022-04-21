package ru.sargassov.fmweb.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "draws")
@Data
@NoArgsConstructor
public class DrawEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "draw")
    private String tour;
}
