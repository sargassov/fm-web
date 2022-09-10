package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_entities.Day;
import ru.sargassov.fmweb.intermediate_repositories.DayIntermediateRepository;
import ru.sargassov.fmweb.intermediate_spi.DayIntermediateServiceSpi;

import java.util.List;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class DayIntermediateService implements DayIntermediateServiceSpi {
    private DayIntermediateRepository repository;

    @Override
    public Day save(Day day) {
        return repository.save(day);
    }

    @Override
    public List<Day> findAllWhereIsMatchTrue() {
        return repository.findAllWhereIsMatchTrue();
    }
}
