package ru.sargassov.fmweb.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api.JuniorPoolApi;
import ru.sargassov.fmweb.converters.JuniorConverter;
import ru.sargassov.fmweb.dto.PlayerDto;
import ru.sargassov.fmweb.dto.PositionDto;
import ru.sargassov.fmweb.repositories.JuniorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class JuniorService {
    private final JuniorRepository juniorRepository;
    private final JuniorConverter juniorConverter;
    private final JuniorPoolApi juniorPoolApi;

    public void createYouthPool(){
        log.info("JuniorService.createYouthPool");
        juniorRepository.findAll().stream()
                .map(juniorConverter::entityToString)
                .forEach(juniorPoolApi::addYouthPlayer);
    }

    public PlayerDto getYoungPlayer(PositionDto positionDto){
        Random random = new Random();
        PlayerDto playerDto = new PlayerDto();
        playerDto.setName(juniorPoolApi.getYouthPlayerName(random.nextInt(juniorPoolApi.getSize())));
        playerDto.setPosition(positionDto);
        juniorConverter.nameToPlayerDto(playerDto);


        return playerDto;
    }
}
