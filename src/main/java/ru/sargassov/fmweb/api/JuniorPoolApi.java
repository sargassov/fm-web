package ru.sargassov.fmweb.api;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.converters.PlayerConverter;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class JuniorPoolApi {
    private final PlayerConverter playerConverter;
    private List<String> youthApiList;
    private int size = 0;

    public void addYouthPlayer(String s){
        if(youthApiList == null)
            youthApiList = new ArrayList<>();
        youthApiList.add(s);
        size += 1;
    }

    public String getYouthPlayerName (int num){
        if(youthApiList.size() > num){
            String name = youthApiList.get(num);
            youthApiList.remove(num);
            size -= 1;
            return name;
        }
        throw new IndexOutOfBoundsException("Wrong player #" + num + " in request");
    }
}