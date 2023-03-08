package ru.sargassov.fmweb.services.intermediate_services;

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
import ru.sargassov.fmweb.repositories.intermediate_repositories.DayIntermediateRepository;
import ru.sargassov.fmweb.spi.intermediate_spi.DayIntermediateServiceSpi;

import javax.transaction.Transactional;
import java.time.LocalDate;
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
        return dayConverter.dtoToPresentDayRequest(repository.findByPresentIsTrueAndUser(UserHolder.user));
    }

    @Override
    public Day findByPresent() {
        return repository.findByPresentIsTrueAndUser(UserHolder.user);
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

    @Override
    public Day findByDate(LocalDate dayDate) {
        return repository.findByDateAndUser(dayDate, UserHolder.user);
    }

    @Override
    public boolean isMatchDay() {
        var day = findByPresent();
        if (!day.isMatch()) {
            return false;
        }

        var userTeam = UserHolder.user.getUserTeam();
        var match = day.getUserTeamMatch(userTeam);
        return !match.isMatchPassed();
    }

    @Override
    @Transactional
    public String addDate() {
        var presentDay = findByPresent();
        var presentDate = presentDay.getDate();
        var tomorrowDate = presentDate.plusDays(1);
        var tomorrowDay = findByDate(tomorrowDate);

        presentDay.setPresent(false);
        presentDay.setPassed(true);
        tomorrowDay.setPresent(true);

        var day = tomorrowDate.getDayOfMonth();
        var month = tomorrowDate.getMonth().toString();
        var year = tomorrowDate.getYear();
        return "Today is " + day + " " + month + " " + year + ".";
    }

    @Override
    public Boolean getShowCondition() {
        var currentDay = findByPresent();
        return !currentDay.isMatch();
    }
}
