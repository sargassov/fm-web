package ru.sargassov.fmweb.api;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.converters.PlayerConverter;
import ru.sargassov.fmweb.intermediate_entites.Player;
import ru.sargassov.fmweb.services.JuniorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Data
@Slf4j
public class JuniorPoolApi {
    private List<String> youthApiList;
    private int size = 0;

    public void setYouthApiList(List<String> youthApiList) {
        this.youthApiList = youthApiList;
    }

    public void addYouthPlayer(String s){
        if(youthApiList == null)
            youthApiList = new ArrayList<>();
        youthApiList.add(s);
        size += 1;
    }

    public String getYouthPlayerName (int num){
        log.info("JuniorPoolApi.getYouthPlayerName");
        if(youthApiList.size() > num){
            String name = youthApiList.remove(num);
            size -= 1;
            return name;
        }
        throw new IndexOutOfBoundsException("Wrong player #" + num + " in request");
    }

    public List<String> getRandomFiveYoungPlyers() {
        List<String> juniors = new ArrayList<>();
        Random random = new Random();

        for (int x = 0; x < 5; x++) {
            juniors.add(youthApiList.get(random.nextInt(youthApiList.size())));
        }
        return juniors;
    }

    public void deleteFromApiList(String name) {
        youthApiList.remove(name);
    }
}