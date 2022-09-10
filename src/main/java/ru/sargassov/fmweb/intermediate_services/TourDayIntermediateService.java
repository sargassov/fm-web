package ru.sargassov.fmweb.intermediate_services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_spi.TourDayIntermediateServiceSpi;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class TourDayIntermediateService implements TourDayIntermediateServiceSpi {
}
