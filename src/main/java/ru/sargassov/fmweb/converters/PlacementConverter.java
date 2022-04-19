package ru.sargassov.fmweb.converters;

import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.PlacementDto;
import ru.sargassov.fmweb.entities.Placement;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlacementConverter {
    public PlacementDto entityToDto(Placement placement){
        PlacementDto pDto = new PlacementDto();
        pDto.setId(placement.getId());
        pDto.setName(placement.getName());
        pDto.setRoles(roleUnpacker(placement.getRoles()));
        return pDto;
    }

    private List<String> roleUnpacker(String roles) {
        roles = roles.substring(1, roles.length() - 1);
        return List.of(roles.split(","));
    }

}
