package ru.sargassov.fmweb.services.entity;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.constants.StadiumAnalytics;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.dto.MarketDto;
import ru.sargassov.fmweb.dto.text_responses.InformationDto;
import ru.sargassov.fmweb.dto.text_responses.StartFinishInformationDto;
import ru.sargassov.fmweb.exceptions.MarketException;
import ru.sargassov.fmweb.intermediate_entities.Market;
import ru.sargassov.fmweb.spi.intermediate_spi.DayIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.MarketIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.entity.MarketServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.TeamIntermediateServiceSpi;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MarketService implements MarketServiceSpi {

    private final DayIntermediateServiceSpi dayIntermediateService;
    private final MarketIntermediateServiceSpi marketIntermediateService;
    private final TeamIntermediateServiceSpi teamIntermediateService;

    @Override
    public List<StartFinishInformationDto> getCurrentmarketsInfo() {
        var team = UserHolder.user.getUserTeam();
        var markets = marketIntermediateService.findByTeam(team);
        var dtos = new ArrayList<StartFinishInformationDto>();

        for (var market : markets) {
            var startDay = market.getStartDate();
            var finishDay = market.getFinishDate();
            var startDate = startDay.getDate();
            var finishDate = finishDay.getDate();

            String startDateString = startDate.getDayOfMonth() + "." + startDate.getMonth() + "." + startDate.getYear();
            String finishDateString = finishDate.getDayOfMonth() + "." + finishDate.getMonth() + "." + finishDate.getYear();
            StartFinishInformationDto dto = new StartFinishInformationDto(
                    market.getMarketTypeInString(),
                    startDateString,
                    finishDateString
            );
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<MarketDto> loadPotencialMarketPrograms() {
        var userTeam = UserHolder.user.getUserTeam();
        return StadiumAnalytics.loadPotencialMarketPrograms(userTeam);
    }

    @Transactional
    @Override
    public void addNewMarketProgram(InformationDto dto) {
        var user = UserHolder.user;
        var teamId = user.getUserTeam().getId();
        var team = teamIntermediateService.getById(teamId);
        var market = Market.getMarketByTitle(dto.getType());
        var multyCoeff = (Integer) dto.getValue();
        var askingCost = market.getMarketType().getOneWeekCost().multiply(BigDecimal.valueOf(multyCoeff));

        if (team.getWealth().compareTo(askingCost) < 0) {
            throw new MarketException("Your club hasn't emough money");
        }
        if (team.getMarkets().size() >= 5) {
            throw new MarketException("Too much marketing program" +
                    "s in your teams!");
        }

        var startDay = dayIntermediateService.findByPresent();
        var finishDate = startDay.getDate().plusWeeks((Integer) dto.getValue());
        var finishDay = dayIntermediateService.findByDate(finishDate);
        market.setStartDate(startDay);
        market.setFinishDate(finishDay);
        market.setTeam(team);
        market.setUser(user);
        team.getMarkets().add(market);
        team.setWealth(team.getWealth().subtract(askingCost));
        team.substractMarketExpenses(askingCost);
        marketIntermediateService.save(market);
    }

    @Transactional
    @Override
    public void rejectProgram(String title) {
        var userTeam = UserHolder.user.getUserTeam();
        userTeam.rejectMarketProgram(title);
    }
}
