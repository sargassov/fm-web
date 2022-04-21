package ru.sargassov.fmweb.api;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.Placement;
import ru.sargassov.fmweb.services.PlacementService;

import java.util.*;

@Component
@Data
public class PlacementApi {
    private List<Placement> placementApiList;
    private int size = 0;

    public void setPlacementApiList(List<Placement> placementApiList) {
        this.placementApiList = placementApiList;
    }

    public Placement getPlacement(Integer num){
        if(placementApiList.size() > num){
            return placementApiList.get(num);
        }
        throw new IndexOutOfBoundsException("Wrong tactic #" + num + " in request");
    }
}
