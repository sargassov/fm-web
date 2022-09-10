package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api_temporary_classes_group.CalendarApi;
import ru.sargassov.fmweb.api_temporary_classes_group.DrawApi;
import ru.sargassov.fmweb.converters.DayConverter;
import ru.sargassov.fmweb.converters.MatchConverter;
import ru.sargassov.fmweb.intermediate_entities.*;
import ru.sargassov.fmweb.dto.days_dtos.DayDto;
import ru.sargassov.fmweb.intermediate_entities.days.TourDay;
import ru.sargassov.fmweb.entities.DayEntity;
import ru.sargassov.fmweb.entity_repositories.DayRepository;
import ru.sargassov.fmweb.intermediate_spi.DayIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.DrawIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.MatchIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.DayServiceSpi;
import ru.sargassov.fmweb.spi.UserServiceSpi;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DayService implements DayServiceSpi {
    private final DayRepository dayRepository;
    private final DayConverter dayConverter;
    private final MatchConverter matchConverter;
    private final DrawIntermediateServiceSpi drawIntermediateService;
    private final DayIntermediateServiceSpi dayIntermediateService; //TODO написать DayIntermediateService
    private final MatchIntermediateServiceSpi matchIntermediateService;

    private final UserServiceSpi userService;

    @Transactional
    @Override
    public List<DayEntity> findAll(){
        return dayRepository.findAll();
    }


    @Override
    public List<Day> getDayIntermediateEntities(User user){
        return findAll().stream()
                .map(de -> dayConverter.getIntermediateEntityFromEntity(de, user))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void loadCalendar(User user){
        var drawList = drawIntermediateService.findAll();
        var calendar = getDayIntermediateEntities(user);

        int countSheduleTours = 1;

        for(Day d : calendar){
            if(d.isMatch()){
                var currentTourDraws = getCurrentTourDraws(drawList, countSheduleTours);
                var notSavedCurrentTourMatches = getNotSavedCurrentTourMatches(currentTourDraws, d, user);
                var matches = matchIntermediateService.save(notSavedCurrentTourMatches);
                d.setMatches(matches);
                d.setCountOfTour(countSheduleTours++);
            }
            dayIntermediateService.save(d);
        }
    }

    private List<Match> getNotSavedCurrentTourMatches(List<Draw> currentTourDraws, Day day, User user) {
        var matchList = new ArrayList<Match>();
        for (var draw : currentTourDraws) {
            var match = matchConverter.getIntermediateEntityFromDraw(draw, day, user);
        }
    }

    private List<Draw> getCurrentTourDraws(List<Draw> drawList, int countSheduleTours) {
        return drawList.stream()
                .filter(d ->d.getTourNumber() == countSheduleTours)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<Day> getCalendarFromApi(){
        return calendarApi.getCalendarApiList();
    }
//    //////////////////////////////////////////////////////////////////////

    @Transactional
    @Override
    public DayDto getActualDateFromApi() {
        return dayConverter.dtoToPresentDayRequest(calendarApi.getPresentDay());
    }

    @Override
    public boolean isMatchday() {
        Day day = calendarApi.getPresentDay();
        if (!day.isMatch()) {
            return false;
        }

        Team userTeam = userService.getUserTeam();
        Match match = ((TourDay) day).getUserTeamMatch(userTeam);
        return !match.isMatchPassed();
    }

    @Override
    public String addDate() {
        Day presentDay = calendarApi.getPresentDay();
        LocalDate presentDate = presentDay.getDate();
        LocalDate tomorrowDate = presentDate.plusDays(1);
        Day tomorrowDay = calendarApi.findByDate(tomorrowDate);

        presentDay.setPresent(false);
        presentDay.setPassed(true);
        tomorrowDay.setPresent(true);

        int day = tomorrowDate.getDayOfMonth();
        String month = tomorrowDate.getMonth().toString();
        int year = tomorrowDate.getYear();
        return "Today is " + day + " " + month + " " + year + ".";
    }

    @Override
    public Day getActualDay() {
        return calendarApi.getPresentDay();
    }
}
