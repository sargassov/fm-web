package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.converters.DayConverter;
import ru.sargassov.fmweb.dto.days_dtos.DayDto;
import ru.sargassov.fmweb.intermediate_entities.Day;
import ru.sargassov.fmweb.intermediate_entities.Position;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_repositories.DayIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.DayIntermediateServiceSpi;

import java.util.ArrayList;
import java.util.List;

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
}
