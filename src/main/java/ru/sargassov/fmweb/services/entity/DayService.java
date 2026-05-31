package ru.sargassov.fmweb.services.entity;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.sargassov.fmweb.converters.DayConverter;
import ru.sargassov.fmweb.enums.PlayOffStage;
import ru.sargassov.fmweb.intermediate_entities.*;
import ru.sargassov.fmweb.entities.DayEntity;
import ru.sargassov.fmweb.repositories.entity.DayRepository;
import ru.sargassov.fmweb.spi.intermediate_spi.DayIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.MatchIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.entity.DayServiceSpi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DayService implements DayServiceSpi {
    private final DayRepository dayRepository;
    private final DayConverter dayConverter;
    private final DayIntermediateServiceSpi dayIntermediateService;
    private final MatchIntermediateServiceSpi matchIntermediateService;

    @Override
    public List<DayEntity> findAll(){
        return dayRepository.findAll();
    }


    @Override
    public List<Day> getDayIntermediateEntities(User user){
        return dayIntermediateService.save(findAll().stream()
                .map(de -> dayConverter.getIntermediateEntityFromEntity(de, user))
                .collect(Collectors.toList()));
    }

    @Transactional
    @Override
    public void loadCalendar(User user){
        var calendar = getDayIntermediateEntities(user);
        var dayList = new ArrayList<Day>();
        var matchList = new ArrayList<Match>();
        int countScheduleLeagueTours = 1;
        int countScheduleCupTours = 1;

        for(Day d : calendar){
            if(d.isLeagueDay()){
                var currentTourMatches = matchIntermediateService.findByUserAndCountOfTourAndLeagueDay(user, countScheduleLeagueTours);
                d.setCountOfTour(countScheduleLeagueTours);
                d.setMatches(currentTourMatches);
                for (var currentMatch : currentTourMatches) {
                    currentMatch.setTourDay(d);
                }
                matchList.addAll(currentTourMatches);
                countScheduleLeagueTours++;
            }

            if (d.isCupDay()) {
                var currentTourMatches = matchIntermediateService.findByUserAndCountOfTourAndCupDay(user, countScheduleCupTours);
                d.setCountOfTour(countScheduleCupTours);
                d.setPlayOffStage(definePlayOffStageByCountOfTour(countScheduleCupTours));
                if (!currentTourMatches.isEmpty()) {
                    d.setMatches(currentTourMatches);
                    for (var currentMatch : currentTourMatches) {
                        currentMatch.setTourDay(d);
                    }
                    matchList.addAll(currentTourMatches);
                }
                countScheduleCupTours++;
            }

            dayList.add(d);
        }
    }

    private PlayOffStage definePlayOffStageByCountOfTour(int countOfTour) {
        if (countOfTour < 3) {
            return PlayOffStage._1_8_FINAL;
        } else if (countOfTour < 5) {
            return PlayOffStage.QUARTERFINAL;
        } else if (countOfTour < 7) {
            return PlayOffStage.SEMIFINAL;
        } else if (countOfTour == 7) {
            return PlayOffStage.FINAL;
        }
        throw new IllegalStateException("Undefined play off stage because count of tour = %s".formatted(countOfTour));
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
