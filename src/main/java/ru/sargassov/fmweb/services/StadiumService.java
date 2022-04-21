package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.dto.Stadium;
import ru.sargassov.fmweb.repositories.StadiumRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class StadiumService {
    private final StadiumRepository stadiumRepository;

    public List<Stadium> getAllStadiums(){
        stadiumRepository.findAll();
        return null;
    }
}
