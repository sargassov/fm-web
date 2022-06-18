package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.constants.FinanceAnalytics;
import ru.sargassov.fmweb.constants.StadiumAnalytics;
import ru.sargassov.fmweb.dto.InformationDto;
import ru.sargassov.fmweb.intermediate_entites.Stadium;
import ru.sargassov.fmweb.intermediate_entites.Team;
import ru.sargassov.fmweb.repositories.StadiumRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class StadiumService {
    private final StadiumRepository stadiumRepository;
    private final UserService userService;

    public List<Stadium> getAllStadiums(){
        stadiumRepository.findAll();
        return null;
    }

    public List<InformationDto> getInfo() {
        Team team = userService.getUserTeam();
        return StadiumAnalytics.getStadiumInforamtion(team);
    }

}
