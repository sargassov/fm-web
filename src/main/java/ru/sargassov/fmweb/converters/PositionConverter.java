package ru.sargassov.fmweb.converters;

import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.PositionDto;

@Component
public class PositionConverter {
    public PositionDto entityToEnum(String title){
        if(title.equals("Goalkeeper")) return PositionDto.GOALKEEPER;
        else if(title.equals("Defender")) return PositionDto.DEFENDER;
        else if(title.equals("Midfielder")) return PositionDto.MIDFIELDER;
        else return PositionDto.FORWARD;
    }
}
