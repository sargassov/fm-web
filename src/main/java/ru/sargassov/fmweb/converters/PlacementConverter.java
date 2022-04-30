package ru.sargassov.fmweb.converters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.Placement;
import ru.sargassov.fmweb.dto.Role;
import ru.sargassov.fmweb.entities.PlacementEntity;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class PlacementConverter {
    public Placement entityToDto(PlacementEntity placementEntity){
        Placement pDto = new Placement();
        pDto.setId(placementEntity.getId());
        pDto.setName(placementEntity.getName());
        pDto.setRoles(roleUnpacker(placementEntity.getRoles()));
        return pDto;
    }

    private List<Role> roleUnpacker(String roles) {
        String splitter = ",";
        String[] rolesSplit = roles.split(splitter);
        List<Role> dtoRoles = new ArrayList<>();

        for(int x = 0; x < rolesSplit.length; x++){
            rolesSplit[x] = purePlacementName(rolesSplit[x]);
            dtoRoles.add(new Role(rolesSplit[x], x));
        }
        return dtoRoles;
    }

    private String purePlacementName(String s) {
        s = s.replace("{", "");
        s = s.replace("}", "");
        return s;
    }

}
