package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.dto.LoanDto;
import ru.sargassov.fmweb.dto.MarketDto;
import ru.sargassov.fmweb.dto.team_dtos.TeamOnPagePlayersDto;
import ru.sargassov.fmweb.dto.player_dtos.IdNamePricePlayerDto;
import ru.sargassov.fmweb.dto.player_dtos.PlayerOnTrainingDto;
import ru.sargassov.fmweb.dto.player_dtos.PlayerSoftSkillDto;
import ru.sargassov.fmweb.dto.text_responses.InformationDto;
import ru.sargassov.fmweb.dto.text_responses.StartFinishInformationDto;
import ru.sargassov.fmweb.dto.text_responses.TextResponse;
import ru.sargassov.fmweb.intermediate_entites.Player;
import ru.sargassov.fmweb.intermediate_entites.Position;
import ru.sargassov.fmweb.intermediate_entites.Role;
import ru.sargassov.fmweb.intermediate_entites.Team;

import java.util.List;

public interface TeamServiceSpi {

    void loadTeams();

    List<Team> findAll();

    void fillTeams(List<Team> teamList);

    void juniorRecruitment(List<Team> teamList);

    void addJuniorToTeam(Team currentTeam, Position currentPosition);

    int randomGuessNum(Team currentTeam);

    List<Team> getTeamListFromApi();

    void fillPlacementForAllTeams();

    void autoFillPlacement(Team t);

    Player findBest(List<Player> suitablePlayers);

    List<Player> getSuitablePlayers(List<Player> playerList, Role role);

    void captainAppointment(Team team);

    void setNewCaptainHandle(String name);

    void powerTeamCounter(Team team);

    TeamOnPagePlayersDto getNameOfUserTeam();

    List<PlayerSoftSkillDto> getAllPlayersByUserTeam(Integer parameter);

    List<PlayerSoftSkillDto> getAllPlayersByAllTeam(Integer parameter);

    void deletePlayerFromCurrentPlacement(Integer number);

    List<PlayerOnTrainingDto> getAllPlayersOnTrainingByUserTeam(Integer parameter);

    TeamOnPagePlayersDto getNameOfOpponentTeam(Integer parameter, Integer delta);

    List<PlayerSoftSkillDto> getTenPlayersFromNextTeam(String name,
                                                       Integer playerParameter, Integer sortParameter);

    void buyNewPlayer(PlayerSoftSkillDto playerSoftSkillDto);

    void sellPlayer(String name);

    List<IdNamePricePlayerDto> getSellingList();

    List<InformationDto> gelAllExpenses();

    TextResponse getStartFinanceMessage();

    List<LoanDto> loadCurrentLoans();

    void remainCurrentLoan(LoanDto loan);

    TextResponse getStartSponsorMessage();

    List<StartFinishInformationDto> getCurrentmarketsInfo();

    void addNewMarketProgram(InformationDto dto);

    List<MarketDto> loadPotencialMarketPrograms();

    void rejectProgram(String title);

    List<InformationDto> getFullCapacityInformation();

    void expandTheStadium(Integer delta);

    List<InformationDto> gelAllIncomes();

    List<String> setTrainingEffects();

    List<String> setFinanceUpdates();

    List<String> setMarketingChanges();

    void setPlayerRecovery();
}
