package ru.sargassov.fmweb.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.sargassov.fmweb.dto.*;
import ru.sargassov.fmweb.dto.player_dtos.IdNamePricePlayerDto;
import ru.sargassov.fmweb.dto.player_dtos.PlayerOnTrainingDto;
import ru.sargassov.fmweb.dto.player_dtos.PlayerSoftSkillDto;
import ru.sargassov.fmweb.dto.team_dtos.TeamOnPagePlayersDto;
import ru.sargassov.fmweb.dto.team_dtos.TeamTransInformationDto;
import ru.sargassov.fmweb.dto.text_responses.InformationDto;
import ru.sargassov.fmweb.dto.text_responses.StartFinishInformationDto;
import ru.sargassov.fmweb.dto.text_responses.TextResponse;
import ru.sargassov.fmweb.spi.intermediate_spi.SponsorIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.StadiumIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.TeamIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.TeamIntermediateServiceSpi2;
import ru.sargassov.fmweb.spi.entity.FinanceServiceSpi;
import ru.sargassov.fmweb.spi.entity.MarketServiceSpi;
import ru.sargassov.fmweb.spi.entity.TrainingServiceSpi;
import ru.sargassov.fmweb.spi.entity.TransferServiceSpi;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class TeamController {
    private final TeamIntermediateServiceSpi teamIntermediateService;
    private final TeamIntermediateServiceSpi2 teamIntermediateService2;
    private final SponsorIntermediateServiceSpi sponsorIntermediateService;
    private final StadiumIntermediateServiceSpi stadiumIntermediateService;
    private final TrainingServiceSpi trainingService;
    private final TransferServiceSpi transferService;
    private final FinanceServiceSpi financeService;
    private final MarketServiceSpi marketService;

    @GetMapping("/team/players/{parameter}")
    public List<PlayerSoftSkillDto> getAllPlayersByUserTeam(@PathVariable Integer parameter) {
        log.info("TeamController.getAllPlayersByUserTeam(parameter)");
        return teamIntermediateService2.getAllPlayersByUserTeam(parameter);
    }

    @GetMapping("/team/name")
    public TeamOnPagePlayersDto getNameOfUserTeam() {
        log.info("TeamController.getNameOfUserTeam()");
        return teamIntermediateService.getNameOfUserTeam();
    }

    @PutMapping("/team/captain")
    public void setNewCaptain(@RequestBody String name) {
        log.info("TeamController.setNewCaptain()");
        teamIntermediateService.setNewCaptainHandle(name);
    }

    @GetMapping("/team/players_on_training/{parameter}")
    public List<PlayerOnTrainingDto> getAllPlayersOnTrainingByUserTeam(@PathVariable Integer parameter) {
        log.info("TeamController.getAllPlayersByUserTeam(parameter)");
        return trainingService.getAllPlayersOnTrainingByUserTeam(parameter);
    }

    @GetMapping("/team/next/players/{parameter}/{i}")
    public TeamOnPagePlayersDto getNextNameOfOpponentTeam(@PathVariable Integer parameter, @PathVariable Integer i) {
        log.info("TeamController.getNextNameOfOpponentTeam()");
        return transferService.getNameOfOpponentTeam(parameter, i);
    }

    @PutMapping("/team/transfer/players")
    public List<PlayerSoftSkillDto> getTenPlayersFromNextTeam(@RequestBody TeamTransInformationDto teamDto) {
        log.info("TeamController.getTenPlayersFromNextByOtherTeam");
        var deltaPlayerParameter = teamDto.getPlayerParameter() + teamDto.getDelta();
        var teamId = teamDto.getId();
        return transferService.getTenPlayersFromNextTeam(deltaPlayerParameter, teamDto.getSortParameter(), teamId);
    }

    @PostMapping("/team/players/buy")
    public void buyNewPlayer(@RequestBody PlayerSoftSkillDto o) {
        log.info("TeamController.buyNewPlayer()");
        transferService.buyNewPlayer(o);
    }

    @DeleteMapping("/team/players/sell/{id}")
    public void sellPlayer(@PathVariable Long id) {
        log.info("TeamController.sellPlayer()");
        transferService.sellPlayer(id);
    }

    @GetMapping("/team/players/selllist")
    public List<IdNamePricePlayerDto> getSellingList() {
        log.info("TeamController.getSellingList(parameter)");
        return transferService.getSellingList();
    }

    @GetMapping("/team/finance/income")
    public List<InformationDto> getAllIncomes() {
        log.info("TeamController.getAllIncomes");
        return financeService.gelAllIncomes();
    }

    @GetMapping("/team/finance/expenses")
    public List<InformationDto> getAllExpenses() {
        log.info("TeamController.getAllExpenses");
        return financeService.gelAllExpenses();
    }

    @GetMapping("/team/finance/start_message")
    public TextResponse getStartFinanceMessage() {
        log.info("TeamController.getStartFinanceMessage");
        return financeService.getStartFinanceMessage();
    }

    @GetMapping("/team/finance/load_loans")
    public List<LoanDto> loadCurrentLoans() {
        log.info("TeamController.loadCurrentLoans");
        return financeService.loadCurrentLoans();
    }

    @PostMapping("/team/finance/reamin_loan")
    public void remainCurrentLoan(@RequestBody LoanDto loan) {
        log.info("TeamController.remainCurrentLoans");
        financeService.remainCurrentLoan(loan);
    }

    @GetMapping("/team/sponspor/start_message")
    public TextResponse getStartSponsorMessage() {
        log.info("TeamController.getStartSponsorMessage");
        return sponsorIntermediateService.getStartSponsorMessage();
    }

    @GetMapping("/team/markets/info")
    public List<StartFinishInformationDto> getCurrentmarketsInfo() {
        log.info("TeamController.getCurrentmarketsInfo");
        return marketService.getCurrentmarketsInfo();
    }

    @PostMapping("/team/market/addNew")
    public void addNewMarketProgram(@RequestBody InformationDto dto) {
        log.info("TeamController.addNewMarketProgram");
        marketService.addNewMarketProgram(dto);
    }

    @GetMapping("/team/market/programs")
    public List<MarketDto> loadPotencialMarketPrograms() {
        log.info("TeamController.loadPotencialMarketPrograms");
        return marketService.loadPotencialMarketPrograms();
    }

    @PostMapping("/team/market/reject")
    public void rejectProgram(@RequestBody String title) {
        log.info("TeamController.rejectProgram");
        marketService.rejectProgram(title);
    }

    @GetMapping("/team/stadium/capacity")
    public List<InformationDto> getFullCapacityInformation() {
        log.info("TeamController.getFullCapacityInformation");
        return stadiumIntermediateService.getFullCapacityInformation();
    }

    @PostMapping("/team/stadium/expand")
    public void expandTheStadium(@RequestBody Integer delta) {
        log.info("TeamController.expandTheStadium");
        stadiumIntermediateService.expandTheStadium(delta);
    }

}
