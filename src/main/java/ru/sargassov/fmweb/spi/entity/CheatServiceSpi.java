package ru.sargassov.fmweb.spi.entity;

import ru.sargassov.fmweb.dto.text_responses.TextResponse;
import ru.sargassov.fmweb.intermediate_entities.User;

public interface CheatServiceSpi {

    void constructCheats(User user);
    TextResponse activateCheat(TextResponse response);

}
