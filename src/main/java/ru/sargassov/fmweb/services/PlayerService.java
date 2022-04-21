package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api.TeamApi;
import ru.sargassov.fmweb.converters.PlayerConverter;
import ru.sargassov.fmweb.dto.Player;
import ru.sargassov.fmweb.repositories.PlayerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerConverter playerConverter;

    @SneakyThrows
    public List<Player> getAllPlayersByTeamId(Long id){
        if(id < 1)
            throw new Exception();

        return playerRepository.findAllByTeamId(id).stream()
                .map(playerConverter::entityToDto).collect(Collectors.toList());
    }
}
