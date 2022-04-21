package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.dto.Position;
import ru.sargassov.fmweb.repositories.PositionRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;

    public List<Position> getAllPositions(){
        positionRepository.findAll();
        return null;
    }
}
