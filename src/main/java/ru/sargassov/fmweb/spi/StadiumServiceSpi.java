package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.dto.text_responses.InformationDto;
import ru.sargassov.fmweb.intermediate_entites.Stadium;

import java.util.List;

public interface StadiumServiceSpi {

    List<Stadium> getAllStadiums();

    List<InformationDto> getInfo();

    List<InformationDto> getCurrentStatusInfo();

    List<InformationDto> getTicketCostInfo();

    void changeTicketCost(InformationDto dto);

    List<InformationDto> getSplitSectorsInfo();

    List<InformationDto> getSectorsCapacityInfo();

    void changeSectorCapacity(InformationDto dto);
}
