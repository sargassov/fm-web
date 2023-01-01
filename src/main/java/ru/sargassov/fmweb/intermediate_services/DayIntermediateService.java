package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.constants.ConstantUtils;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.converters.DayConverter;
import ru.sargassov.fmweb.dto.days_dtos.DayDto;
import ru.sargassov.fmweb.intermediate_entities.Day;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_repositories.DayIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.DayIntermediateServiceSpi;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class DayIntermediateService implements DayIntermediateServiceSpi {
    private DayIntermediateRepository repository;
    private DayConverter dayConverter;

    @Override
    public Day save(Day day) {
        return repository.save(day);
    }

    @Override
    public List<Day> findByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    public List<Day> save(List<Day> days) {
        return repository.saveAll(days);
    }

    @Override
    public DayDto getActualDate() {
        return dayConverter.dtoToPresentDayRequest(repository.findByPresentIsTrueAnduser(UserHolder.user));
    }

    @Override
    public Day findByPresent() {
        return repository.findByPresentIsTrueAnduser(UserHolder.user);
    }

    @Override
    public List<Day> loacAllMatchDates() {
        return repository.loacAllMatchDates(UserHolder.user);
    }

    @Override
    public Day getTour(Integer parameter) {
        return repository.findByCountOfTourAndUser(parameter, UserHolder.user);
    }

    @Override
    public List<Day> getMonth(Integer parameter) {
        var allCalendarDates = repository.getCalendar(UserHolder.user);
        var month = ConstantUtils.getMonthByParameter(parameter);
        return allCalendarDates.stream()
                .filter(d -> d.getDate().getMonth().equals(month))
                .sorted(Comparator.comparing(d -> d.getDate().getMonth()))
                .collect(Collectors.toList());
    }
}
