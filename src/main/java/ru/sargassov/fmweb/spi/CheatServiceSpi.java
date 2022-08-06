package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.dto.text_responses.TextResponse;

public interface CheatServiceSpi {

    void constructCheats();
    TextResponse activateCheat(TextResponse response);

}
