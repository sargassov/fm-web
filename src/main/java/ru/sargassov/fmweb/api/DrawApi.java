package ru.sargassov.fmweb.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.exceptions.SheduleInTourNotFoundException;
import ru.sargassov.fmweb.services.DrawService;

import java.util.List;

@Component
@AllArgsConstructor
@Getter
public class DrawApi {
    private List<List<String>> sheduleApiList;

    public void setSheduleApiList(List<List<String>> sheduleApiList) {
        this.sheduleApiList = sheduleApiList;
    }

    public List<String> getTourSheduleByTourNum(int num){
        return sheduleApiList.stream()
                .filter(s -> sheduleApiList.get(num).equals(s))
                .findFirst()
                .orElseThrow(() ->
                        new SheduleInTourNotFoundException(String.format("Tour '%d' not found in shedule", num)));
    }
}
