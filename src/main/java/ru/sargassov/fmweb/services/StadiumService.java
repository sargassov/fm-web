package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.constants.StadiumAnalytics;
import ru.sargassov.fmweb.dto.InformationDto;
import ru.sargassov.fmweb.exceptions.StadiumException;
import ru.sargassov.fmweb.intermediate_entites.Stadium;
import ru.sargassov.fmweb.intermediate_entites.Team;
import ru.sargassov.fmweb.repositories.StadiumRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class StadiumService {
    private final StadiumRepository stadiumRepository;
    private final UserService userService;

    public List<Stadium> getAllStadiums(){
        stadiumRepository.findAll();
        return null;
    }

    public List<InformationDto> getInfo() {
        Team userTeam = userService.getUserTeam();
        return StadiumAnalytics.getStadiumInforamtion(userTeam);
    }

    public List<InformationDto> getCurrentStatusInfo() {
        Team userTeam = userService.getUserTeam();
        return StadiumAnalytics.getCurrentStadiumStatus(userTeam);
    }

    public List<InformationDto> getTicketCostInfo() {
        Team userTeam = userService.getUserTeam();
        return StadiumAnalytics.getTicketCostInfo(userTeam);
    }

    public void changeTicketCost(InformationDto dto) {
        Stadium stadium = userService.getUserTeam().getStadium();
        BigDecimal typeTicketCost = stadium.getCostByTypeOfSector(dto.getType());
        BigDecimal decimalDelta = BigDecimal.valueOf((Integer) dto.getValue()).divide(BigDecimal.valueOf(1_000_000));
        BigDecimal zero = BigDecimal.ZERO;

        if(typeTicketCost.add(decimalDelta).compareTo(zero) < 0) {
            log.error("Cost of place on sector can't be less than 0!");
            throw new StadiumException("Cost of place on sector can't be less than 0!");
        }

        typeTicketCost = typeTicketCost.add(decimalDelta);
        stadium.setTicketCostByTypeOfSector(dto.getType(), typeTicketCost);
    }

    public List<InformationDto> getSplitSectorsInfo() {
        Team userTeam = userService.getUserTeam();
        return StadiumAnalytics.getSplitSectorsInfo(userTeam);
    }

    public List<InformationDto> getSectorsCapacityInfo() {
        Team userTeam = userService.getUserTeam();
        return StadiumAnalytics.getSectorsCapacityInfo(userTeam);
    }

    public void changeSectorCapacity(InformationDto dto) {
        Team userTeam = userService.getUserTeam();
        Stadium stadium = userTeam.getStadium();
        int delta = (int) dto.getValue();
        String description = dto.getType();

        if (!stadium.approveExtense(description, delta)) {
            String message = String.format("You can't make extension with %s to %d!",  stadium.getTitle(), delta);
            log.error(message);
            throw new StadiumException(String.format(message));
        }
        stadium.makeExtension(description, delta);
        userTeam.setWealth(userTeam.getWealth().subtract(BigDecimal.valueOf(1)));
    }
}
