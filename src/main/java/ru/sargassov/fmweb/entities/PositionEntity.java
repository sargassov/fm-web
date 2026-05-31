package ru.sargassov.fmweb.entities;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "positions")
@Data
@NoArgsConstructor
public class PositionEntity extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "short_title")
    private String shortTitle;
}
