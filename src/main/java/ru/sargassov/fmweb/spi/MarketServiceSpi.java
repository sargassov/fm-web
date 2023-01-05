package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.dto.MarketDto;
import ru.sargassov.fmweb.dto.text_responses.InformationDto;
import ru.sargassov.fmweb.dto.text_responses.StartFinishInformationDto;

import java.util.List;

public interface MarketServiceSpi {
    List<StartFinishInformationDto> getCurrentmarketsInfo();

    List<MarketDto> loadPotencialMarketPrograms();

    void addNewMarketProgram(InformationDto dto);

    void rejectProgram(String title);
}
