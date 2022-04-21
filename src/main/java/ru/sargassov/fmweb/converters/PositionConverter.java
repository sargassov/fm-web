package ru.sargassov.fmweb.converters;

import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.Position;

@Component
public class PositionConverter {
    public Position entityToEnum(String title){
        if(title.equals("Goalkeeper")) return Position.GOALKEEPER;
        else if(title.equals("Defender")) return Position.DEFENDER;
        else if(title.equals("Midfielder")) return Position.MIDFIELDER;
        else return Position.FORWARD;
    }
}
