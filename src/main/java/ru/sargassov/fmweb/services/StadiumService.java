package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.StadiumConverter;
import ru.sargassov.fmweb.intermediate_entities.Stadium;
import ru.sargassov.fmweb.entity_repositories.StadiumRepository;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_spi.LeagueIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.StadiumIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.StadiumServiceSpi;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class StadiumService implements StadiumServiceSpi {
    private final StadiumRepository stadiumRepository;
    private final StadiumConverter stadiumConverter;
    private final LeagueIntermediateServiceSpi leagueIntermediateService;
    private final StadiumIntermediateServiceSpi stadiumIntermediateService;

    @Override
    @Transactional
    public List<Stadium> getAllStadiums(){
        stadiumRepository.findAll();
        return null;
    }

    @Override
    public void loadStadiums(User user) {
        var stadiumEntites = stadiumRepository.findAll();
        var league = leagueIntermediateService.getLeague(user);
        var notSavedStadiums = stadiumEntites
                .stream()
                .map(enitity -> stadiumConverter.getIntermediateEntityFromEntity(enitity, user, league))
                .collect(Collectors.toList());
        stadiumIntermediateService.save(notSavedStadiums);
    }



//    @Override
//    @Transactional
//    public List<InformationDto> getCurrentStatusInfo() {
//        Team userTeam = userService.getUserTeam();
//        return StadiumAnalytics.getCurrentStadiumStatus(userTeam);
//    }

//    @Override
//    @Transactional
//    public List<InformationDto> getTicketCostInfo() {
//        Team userTeam = userService.getUserTeam();
//        return StadiumAnalytics.getTicketCostInfo(userTeam);
//    }

//    @Override
//    @Transactional
//    public void changeTicketCost(InformationDto dto) {
//        Stadium stadium = userService.getUserTeam().getStadium();
//        BigDecimal typeTicketCost = stadium.getCostByTypeOfSector(dto.getType());
//        BigDecimal decimalDelta = BigDecimal.valueOf((Integer) dto.getValue()).divide(BigDecimal.valueOf(1_000_000));
//        BigDecimal zero = BigDecimal.ZERO;
//
//        if(typeTicketCost.add(decimalDelta).compareTo(zero) < 0) {
//            log.error("Cost of place on sector can't be less than 0!");
//            throw new StadiumException("Cost of place on sector can't be less than 0!");
//        }
//
//        typeTicketCost = typeTicketCost.add(decimalDelta);
//        stadium.setTicketCostByTypeOfSector(dto.getType(), typeTicketCost);
//    }

//    @Override
//    @Transactional
//    public List<InformationDto> getSplitSectorsInfo() {
//        Team userTeam = userService.getUserTeam();
//        return StadiumAnalytics.getSplitSectorsInfo(userTeam);
//    }
//
//    @Override
//    @Transactional
//    public List<InformationDto> getSectorsCapacityInfo() {
//        Team userTeam = userService.getUserTeam();
//        return StadiumAnalytics.getSectorsCapacityInfo(userTeam);
//    }
//
//    @Override
//    @Transactional
//    public void changeSectorCapacity(InformationDto dto) {
//        Team userTeam = userService.getUserTeam();
//        Stadium stadium = userTeam.getStadium();
//        int delta = (int) dto.getValue();
//        String description = dto.getType();
//
//        if (!stadium.approveExtense(description, delta)) {
//            String message = String.format("You can't make extension with %s to %d!",  stadium.getTitle(), delta);
//            log.error(message);
//            throw new StadiumException(String.format(message));
//        }
//        stadium.makeExtension(description, delta);
//        userTeam.setWealth(userTeam.getWealth().subtract(BigDecimal.valueOf(1)));
//    }
}
