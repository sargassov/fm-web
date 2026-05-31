package ru.sargassov.fmweb.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cups")
@Data
@NoArgsConstructor
public class CupEntity extends BaseChallengeEntity {
}