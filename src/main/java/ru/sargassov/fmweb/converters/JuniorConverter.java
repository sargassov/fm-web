package ru.sargassov.fmweb.converters;

import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.entities.Junior;

@Component
public class JuniorConverter {
    public String entityToString(Junior junior){
        return junior.getName();
    }
}
