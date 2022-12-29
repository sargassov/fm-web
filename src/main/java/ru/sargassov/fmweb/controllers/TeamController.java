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
import ru.sargassov.fmweb.intermediate_spi.TeamIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.TeamServiceSpi;
import ru.sargassov.fmweb.spi.TrainingServiceSpi;
import ru.sargassov.fmweb.spi.TransferServiceSpi;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class TeamController {
    private final TeamIntermediateServiceSpi teamIntermediateService;
    private final TrainingServiceSpi trainingService;
    private final TransferServiceSpi transferService;

    @GetMapping("/team/players/{parameter}")
    public List<PlayerSoftSkillDto> getAllPlayersByUserTeam(@PathVariable Integer parameter) {
        log.info("TeamController.getAllPlayersByUserTeam(parameter)");
        return teamIntermediateService.getAllPlayersByUserTeam(parameter);
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
//        return teamService.gelAllIncomes();
        return null;
    }

    @GetMapping("/team/finance/expenses")
    public List<InformationDto> getAllExpenses() {
        log.info("TeamController.getAllExpenses");
//        return teamService.gelAllExpenses();
        return null;
    }

    @GetMapping("/team/finance/start_message")
    public TextResponse getStartFinanceMessage() {
        log.info("TeamController.getStartFinanceMessage");
//        return teamService.getStartFinanceMessage();
        return null;
    }

    @GetMapping("/team/finance/load_loans")
    public List<LoanDto> loadCurrentLoans() {
        log.info("TeamController.loadCurrentLoans");
//        return teamService.loadCurrentLoans();
        return null;
    }

    @PostMapping("/team/finance/reamin_loan")
    public void remainCurrentLoan(@RequestBody LoanDto loan) {
        log.info("TeamController.remainCurrentLoans");
//        teamService.remainCurrentLoan(loan);
    }

    @GetMapping("/team/sponspor/start_message")
    public TextResponse getStartSponsorMessage() {
        log.info("TeamController.getStartSponsorMessage");
//        return teamService.getStartSponsorMessage();
        return null;
    }

    @GetMapping("/team/markets/info")
    public List<StartFinishInformationDto> getCurrentmarketsInfo() {
        log.info("TeamController.getCurrentmarketsInfo");
//        return teamService.getCurrentmarketsInfo();
        return null;
    }

    @PostMapping("/team/market/addNew")
    public void addNewMarketProgram(@RequestBody InformationDto dto) {
        log.info("TeamController.addNewMarketProgram");
//        teamService.addNewMarketProgram(dto);
    }

    @GetMapping("/team/market/programs")
    public List<MarketDto> loadPotencialMarketPrograms() {
        log.info("TeamController.loadPotencialMarketPrograms");
//        return teamService.loadPotencialMarketPrograms();
        return null;
    }

    @PostMapping("/team/market/reject")
    public void rejectProgram(@RequestBody String title) {
        log.info("TeamController.rejectProgram");
//        teamService.rejectProgram(title);
    }

    @GetMapping("/team/stadium/capacity")
    public List<InformationDto> getFullCapacityInformation() {
        log.info("TeamController.getFullCapacityInformation");
//        return teamService.getFullCapacityInformation();
        return null;
    }

    @PostMapping("/team/stadium/expand")
    public void expandTheStadium(@RequestBody Integer delta) {
        log.info("TeamController.expandTheStadium");
//        teamService.expandTheStadium(delta);
    }

}
