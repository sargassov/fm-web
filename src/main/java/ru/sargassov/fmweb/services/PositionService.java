package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.intermediate_entites.Position;
import ru.sargassov.fmweb.repositories.PositionRepository;
import ru.sargassov.fmweb.spi.PositionServiceSpi;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class PositionService implements PositionServiceSpi {
    private final PositionRepository positionRepository;

    @Override
    @Transactional
    public List<Position> getAllPositions(){
        positionRepository.findAll();
        return null;
    }
}
