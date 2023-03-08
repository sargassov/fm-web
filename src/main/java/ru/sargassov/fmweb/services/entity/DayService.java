package ru.sargassov.fmweb.services.entity;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.DayConverter;
import ru.sargassov.fmweb.intermediate_entities.*;
import ru.sargassov.fmweb.entities.DayEntity;
import ru.sargassov.fmweb.repositories.entity.DayRepository;
import ru.sargassov.fmweb.spi.intermediate_spi.DayIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.MatchIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.entity.DayServiceSpi;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DayService implements DayServiceSpi {
    private final DayRepository dayRepository;
    private final DayConverter dayConverter;
    private final DayIntermediateServiceSpi dayIntermediateService;
    private final MatchIntermediateServiceSpi matchIntermediateService;

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


    @Override
    public void loadCalendar(User user){
        var calendar = getDayIntermediateEntities(user);
        var dayList = new ArrayList<Day>();
        var matchList = new ArrayList<Match>();
        int countSheduleTours = 1;

        for(Day d : calendar){
            if(d.isMatch()){
                var countOfTour = countSheduleTours++;
                d.setCountOfTour(countOfTour);
                var currentTourMatches = matchIntermediateService.findByUserAndCountOfTour(user, countOfTour);
                d.setMatches(currentTourMatches);
                for (var currentMatch : currentTourMatches) {
                    currentMatch.setTourDay(d);
                }
                matchList.addAll(currentTourMatches);
            }
            dayList.add(d);
        }
        dayIntermediateService.save(dayList);
        matchIntermediateService.save(matchList);
    }

//    @Transactional
//    @Override
//    public List<Day> getCalendarFromApi(){
//        return calendarApi.getCalendarApiList();
//    }
//    //////////////////////////////////////////////////////////////////////

//    @Transactional
//    @Override
//    public DayDto getActualDateFromApi() {
//        return dayConverter.dtoToPresentDayRequest(calendarApi.getPresentDay());
//    }
//
//    @Override
//    public boolean isMatchday() {
//        Day day = calendarApi.getPresentDay();
//        if (!day.isMatch()) {
//            return false;
//        }
//
//        Team userTeam = userService.getUserTeam();
//        Match match = ((TourDay) day).getUserTeamMatch(userTeam);
//        return !match.isMatchPassed();
//    }

//    @Override
//    public String addDate() {
//        Day presentDay = calendarApi.getPresentDay();
//        LocalDate presentDate = presentDay.getDate();
//        LocalDate tomorrowDate = presentDate.plusDays(1);
//        Day tomorrowDay = calendarApi.findByDate(tomorrowDate);
//
//        presentDay.setPresent(false);
//        presentDay.setPassed(true);
//        tomorrowDay.setPresent(true);
//
//        int day = tomorrowDate.getDayOfMonth();
//        String month = tomorrowDate.getMonth().toString();
//        int year = tomorrowDate.getYear();
//        return "Today is " + day + " " + month + " " + year + ".";
//    }
//
//    @Override
//    public Day getActualDay() {
//        return calendarApi.getPresentDay();
//    }
}
