package ru.sargassov.fmweb.spi.entity;

import ru.sargassov.fmweb.dto.days_dtos.DayDto;
import ru.sargassov.fmweb.dto.text_responses.PostMatchDto;
import ru.sargassov.fmweb.dto.text_responses.PreMatchDto;
import ru.sargassov.fmweb.intermediate_entities.User;

import java.util.List;

public interface MatchServiceSpi {
    void loadmatches(User user);
    List<PreMatchDto> getPreMatchInfo();

    void imitate(DayDto dayDto);

    List<PostMatchDto> getPostMatchInfo();
}
