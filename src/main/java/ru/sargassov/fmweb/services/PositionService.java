package ru.sargassov.fmweb.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.dto.PositionDto;
import ru.sargassov.fmweb.repositories.PositionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;

    public List<PositionDto> getAllPositions(){
        positionRepository.findAll();
        return null;
    }
}
