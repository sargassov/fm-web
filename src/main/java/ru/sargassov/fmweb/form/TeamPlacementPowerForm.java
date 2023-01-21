package ru.sargassov.fmweb.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.sargassov.fmweb.intermediate_entities.Placement;

@Data
@AllArgsConstructor
public class TeamPlacementPowerForm {
    private String description;
    private Integer currentPlacementTeamPower;
    private Placement placement;
}
