package ru.sargassov.fmweb.spi.entity;

import ru.sargassov.fmweb.dto.text_responses.TextResponse;

import java.util.List;

public interface NewDayServiceSpi {
    Boolean createNewDay();

    List<TextResponse> loadChanges();
}
