package ru.sargassov.fmweb.intermediate_entities;

import lombok.*;
import ru.sargassov.fmweb.constants.BaseUserEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "sponsor")
@Getter
@Setter
@RequiredArgsConstructor
public class Sponsor extends BaseUserEntity {


    @Column(name = "name")
    private String name;

    @Column(name = "id_sponsor_entity")
    private Long sponsorEntityId;

    @Column(name = "day_wage")
    private BigDecimal dayWage;

    @Column(name = "match_wage")
    private BigDecimal matchWage;

    @Column(name = "goal_bonus_wage")
    private BigDecimal goalBonusWage;

    @Column(name = "win_wage")
    private BigDecimal winWage;

    @Column(name = "deuce_wage")
    private BigDecimal deuceWage;

    @Column(name = "contract_bonus_wage")
    private BigDecimal contractBonusWage;

    public void signContractWithClub(Team t){
        t.setWealth(t.getWealth().add(contractBonusWage));
    }
}