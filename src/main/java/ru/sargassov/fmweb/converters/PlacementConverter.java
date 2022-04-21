package ru.sargassov.fmweb.converters;

import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.Placement;
import ru.sargassov.fmweb.entities.PlacementEntity;

import java.util.List;

@Component
public class PlacementConverter {
    public Placement entityToDto(PlacementEntity placementEntity){
        Placement pDto = new Placement();
        pDto.setId(placementEntity.getId());
        pDto.setName(placementEntity.getName());
        pDto.setRoles(roleUnpacker(placementEntity.getRoles()));
        return pDto;
    }

    private List<String> roleUnpacker(String roles) {
        roles = roles.substring(1, roles.length() - 1);
        return List.of(roles.split(","));
    }

}
