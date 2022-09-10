package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api_temporary_classes_group.CalendarApi;
import ru.sargassov.fmweb.constants.Constant;
import ru.sargassov.fmweb.converters.CalendarConverter;
import ru.sargassov.fmweb.dto.IntegerDto;
import ru.sargassov.fmweb.dto.NameOfMonthDto;
import ru.sargassov.fmweb.dto.days_dtos.EventDto;
import ru.sargassov.fmweb.exceptions.MatchException;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.Day;
import ru.sargassov.fmweb.intermediate_entities.Match;
import ru.sargassov.fmweb.intermediate_entities.days.TourDay;
import ru.sargassov.fmweb.spi.CalendarServiceSpi;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CalendarService implements CalendarServiceSpi {
    private final CalendarApi calendarApi;
    private final CalendarConverter calendarConverter;

    @Transactional
    @Override
    public EventDto getTour(Integer parameter) {
        if (parameter == 0) parameter = 30;
        if (parameter == 31) parameter = 1;
        return calendarConverter.getEventDtoFromTourDayEntity(
                calendarApi.getTour(parameter));
    }

    @Transactional
    @Override
    public List<EventDto> getMonth(Integer parameter) {
        if (parameter < 0) parameter = 10;
        if (parameter > 10) parameter = 0;
        return calendarApi.getMonth(parameter).stream()
                .map(calendarConverter::getEventDtoFromDayEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public NameOfMonthDto getMonthName(Integer parameter) {
        if(parameter == -1) parameter = 10;
        if(parameter == 11) parameter = 0;
        String s = Constant.Month.values()[parameter].toString();
        return calendarConverter.getNameOfMonthDtoFromConstant(s);
    }

    @Transactional
    @Override
    public Day getPresentDay() {
        return calendarApi.getPresentDay();
    }

    @Override
    public Match findCurrentMatch(Team homeTeam, Team awayTeam) {
        for (int x = 1; x <= 30; x++) {
            TourDay tour = calendarApi.getTour(x);
            for (int y = 0; y < tour.getMatches().size(); y++) {
                Match match = tour.getMatches().get(y);

                if (match.getHome().equals(homeTeam) &&
                        match.getAway().equals(awayTeam)) {
                    return match;
                }
            }
        }
        log.error("Match between " + homeTeam + " and " + awayTeam + " not found!");
        throw new MatchException("Match between " + homeTeam + " and " + awayTeam + " not found!");
    }

    @Override
    public IntegerDto getMonthParameter() {
        IntegerDto iDto = new IntegerDto();
        iDto.setParameter(calendarApi.getMonthParameter());
        return iDto;
    }

    @Override
    public IntegerDto getTourParameter() {
        IntegerDto iDto = new IntegerDto();
        iDto.setParameter(calendarApi.getTourParameter());
        return iDto;
    }
}
