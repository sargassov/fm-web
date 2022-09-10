package ru.sargassov.fmweb.intermediate_entities;

import lombok.*;
import ru.sargassov.fmweb.constants.BaseUserEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cortage")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Cortage extends BaseUserEntity {

    @OneToOne
    @JoinColumn(name = "id_team")
    private Team team;

    @OneToMany(mappedBy = "cortage", cascade = CascadeType.ALL)
    @JoinColumn(name = "match_list")
    private List<Match> matchList;

    public List<Match> getMatchList() {
        if (matchList == null) {
            matchList = new ArrayList<>();
        }
        return matchList;
    }
}
