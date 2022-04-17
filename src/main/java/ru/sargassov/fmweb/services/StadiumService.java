package ru.sargassov.fmweb.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.dto.StadiumDto;
import ru.sargassov.fmweb.repositories.StadiumRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StadiumService {
    private final StadiumRepository stadiumRepository;

    public List<StadiumDto> getAllStadiums(){
        stadiumRepository.findAll();
        return null;
    }
}
