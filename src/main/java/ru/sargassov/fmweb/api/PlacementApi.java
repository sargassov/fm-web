package ru.sargassov.fmweb.api;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.PlacementDto;

import java.util.*;

@Component
@Data
public class PlacementApi {
    private List<PlacementDto> placementApiList;
    private int size = 0;

    public void addPlacementDto(PlacementDto placementDto){
        if(placementApiList == null)
            placementApiList = new ArrayList<>();
        placementApiList.add(placementDto);
        size += 1;
    }

    public PlacementDto getPlacementDto(Integer num){
        if(placementApiList.size() > num){
            return placementApiList.get(num);
        }
        throw new IndexOutOfBoundsException("Wrong tactic #" + num + " in request");
    }
}
