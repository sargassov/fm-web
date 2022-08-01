package ru.sargassov.fmweb.api_temporary_classes_group;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.exceptions.PresentDayNotFoundException;
import ru.sargassov.fmweb.intermediate_entites.Placement;

import java.util.*;

@Component
@Data
public class PlacementApi { //TEMPORARY CLASS
    private List<Placement> placementApiList;
    private int size = 0;

    public void setPlacementApiList(List<Placement> placementApiList) {
        this.placementApiList = placementApiList;
    }

    public Placement getPlacementByNumber(Integer num){
        if(placementApiList.size() > num){
            return placementApiList.get(num);
        }
        throw new IndexOutOfBoundsException("Wrong tactic #" + num + " in request");
    }

    public Placement getPlacementByTitle(String title){
        return placementApiList.stream()
                .filter(p -> p.getName().equals(title))
                .findFirst()
                .orElseThrow(() ->
                        new PresentDayNotFoundException("Present day not found in calendar"));
    }
}
