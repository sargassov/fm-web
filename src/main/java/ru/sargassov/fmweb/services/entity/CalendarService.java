package ru.sargassov.fmweb.services.entity;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.constants.Constant;
import ru.sargassov.fmweb.constants.ConstantUtils;
import ru.sargassov.fmweb.converters.CalendarConverter;
import ru.sargassov.fmweb.dto.IntegerDto;
import ru.sargassov.fmweb.dto.NameOfMonthDto;
import ru.sargassov.fmweb.dto.days_dtos.EventDto;
import ru.sargassov.fmweb.intermediate_entities.Day;
import ru.sargassov.fmweb.spi.intermediate_spi.DayIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.entity.CalendarServiceSpi;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CalendarService implements CalendarServiceSpi {
    private final CalendarConverter calendarConverter;
    private final DayIntermediateServiceSpi dayIntermediateService;

    @Transactional
    @Override
    public EventDto getTour(Integer parameter) {
        if (parameter == 0) {
            parameter = 30;
        }
        if (parameter == 31) {
            parameter = 1;
        }
        var currentTour = dayIntermediateService.getTour(parameter);
        return calendarConverter.getEventDtoFromTourDayEntity(currentTour);
    }

    @Transactional
    @Override
    public List<EventDto> getMonth(Integer parameter) {
        if (parameter < 0) parameter = 10;
        if (parameter > 10) parameter = 0;
        return dayIntermediateService.getMonth(parameter).stream()
                .sorted(Comparator.comparing(Day::getDate))
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

//    @Transactional
//    @Override
//    public Day getPresentDay() {
//        return calendarApi.getPresentDay();
//    }

//    @Override
//    public Match findCurrentMatch(Team homeTeam, Team awayTeam) {
//        for (int x = 1; x <= 30; x++) {
//            TourDay tour = calendarApi.getTour(x);
//            for (int y = 0; y < tour.getMatches().size(); y++) {
//                Match match = tour.getMatches().get(y);
//
//                if (match.getHome().equals(homeTeam) &&
//                        match.getAway().equals(awayTeam)) {
//                    return match;
//                }
//            }
//        }
//        log.error("Match between " + homeTeam + " and " + awayTeam + " not found!");
//        throw new MatchException("Match between " + homeTeam + " and " + awayTeam + " not found!");
//    }

    @Override
    public IntegerDto getMonthParameter() {
        var actualDay = dayIntermediateService.findByPresent();
        var actualMonthValue = ConstantUtils.getActualMonthValue(actualDay);
        IntegerDto iDto = new IntegerDto();
        iDto.setParameter(actualMonthValue);
        return iDto;
    }

    @Override
    public IntegerDto getTourParameter() {
        var actualDay = dayIntermediateService.findByPresent();
        var actualLocalDate = actualDay.getDate();
        var allTourDates = dayIntermediateService.loacAllMatchDates();

        var currentTour = allTourDates.stream()
                .filter(md -> !md.getDate().isBefore(actualLocalDate))
                .min(Comparator.comparing(Day::getDate))
                .map(Day::getCountOfTour)
                .orElse(30);

        var iDto = new IntegerDto();
        iDto.setParameter(currentTour);
        return iDto;
    }
}
