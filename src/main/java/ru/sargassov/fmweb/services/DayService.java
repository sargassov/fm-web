package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api_temporary_classes_group.CalendarApi;
import ru.sargassov.fmweb.converters.DayConverter;
import ru.sargassov.fmweb.intermediate_entites.days.Day;
import ru.sargassov.fmweb.dto.days_dtos.DayDto;
import ru.sargassov.fmweb.intermediate_entites.days.TourDay;
import ru.sargassov.fmweb.entities.DayEntity;
import ru.sargassov.fmweb.repositories.DayRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DayService {
    private final DayRepository dayRepository;
    private final DayConverter dayConverter;
    private final CalendarApi calendarApi;
    private final DrawService drawService;

    private List<DayEntity> findAll(){
        return dayRepository.findAll();
    }

    private List<Day> convertAllToDto(){
        return findAll().stream()
                .map(dayConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public void loadCalendar(){
        List<List<String>> shedule = drawService.getDrawsFromApi();
        List<Day> calendar = convertAllToDto();
        int countSheduleTours = 0;

        for(Day d : calendar){
            if(d instanceof TourDay){
                ((TourDay) d).setMatches(
                        dayConverter.descriptionOfTourToMatches(shedule.get(countSheduleTours++)));
                ((TourDay) d).setCountOfTour(countSheduleTours);
            }
        }
        calendarApi.setCalendarApiList(calendar);
    }

    public List<Day> getCalendarFromApi(){
        return calendarApi.getCalendarApiList();
    }
//    //////////////////////////////////////////////////////////////////////

    public DayDto getActualDateFromApi() {
        return dayConverter.dtoToPresentDayRequest(calendarApi.getPresentDay());
    }
}
