package ru.sargassov.fmweb.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.JuniorConverter;
import ru.sargassov.fmweb.repositories.JuniorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JuniorService {
    private final JuniorRepository juniorRepository;
    private final JuniorConverter juniorConverter;

    public List<String> getAllJuniors(){
        log.info("JuniorService.getAllJuniors");
        return juniorRepository.findAll().stream()
                .map(juniorConverter::entityToString).collect(Collectors.toList());
    }
}
