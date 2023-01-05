package ru.sargassov.fmweb.intermediate_spi;

import ru.sargassov.fmweb.dto.text_responses.InformationDto;
import ru.sargassov.fmweb.intermediate_entities.Stadium;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

public interface StadiumIntermediateServiceSpi {
    List<Stadium> save(List<Stadium> notSavedStadiums);

    Stadium findByStadiumEntityIdAndUser(Long stadiumEntityId, User user);

    Stadium save(Stadium stadium);

    void assignTeamsToStadiums(List<Team> teams);

    List<InformationDto> getInfo();

    List<InformationDto> getCurrentStatusInfo();

    List<InformationDto> getTicketCostInfo();

    void changeTicketCost(InformationDto dto);

    List<InformationDto> getSectorsCapacityInfo();

    List<InformationDto> getSplitSectorsInfo();

    void changeSectorCapacity(InformationDto dto);

    Boolean getShowOtherMarketProgramsCondition();

    List<InformationDto> getFullCapacityInformation();

    void expandTheStadium(Integer delta);
}
