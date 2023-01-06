package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.enums.PositionType;
import ru.sargassov.fmweb.intermediate_entities.*;

import java.util.List;

public interface TeamServiceSpi {

    void loadTeams(User user);

    List<Team> findAll(User user, League league);

    void fillTeams(List<Team> teamList, User user, League league);

    void juniorRecruitment(User user);

    void addJuniorToTeam(Team currentTeam, PositionType currentPosition, User user, List<Junior> allJuniors);

    int randomGuessNum(Team currentTeam);

    void addJuniorToTeam(Team sellerTeam, PositionType position);

//    List<Team> getTeamListFromApi();
//
//    void captainAppointment(Team team);
//
//    void setNewCaptainHandle(String name);
//
//    void powerTeamCounter(Team team);
//
//    TeamOnPagePlayersDto getNameOfUserTeam();
//
//    List<PlayerSoftSkillDto> getAllPlayersByUserTeam(Integer parameter);
//
//    List<PlayerSoftSkillDto> getAllPlayersByAllTeam(Integer parameter);
//
//    void deletePlayerFromCurrentPlacement(Integer number);
//
//    List<PlayerOnTrainingDto> getAllPlayersOnTrainingByUserTeam(Integer parameter);
//
//    TeamOnPagePlayersDto getNameOfOpponentTeam(Integer parameter, Integer delta);
//
//    List<PlayerSoftSkillDto> getTenPlayersFromNextTeam(String name,
//                                                       Integer playerParameter, Integer sortParameter);
//
//    void buyNewPlayer(PlayerSoftSkillDto playerSoftSkillDto);
//
//    void sellPlayer(String name);
//
//    List<IdNamePricePlayerDto> getSellingList();
//
//    List<InformationDto> gelAllExpenses();
//
//    TextResponse getStartFinanceMessage();
//
//    List<LoanDto> loadCurrentLoans();
//
//    void remainCurrentLoan(LoanDto loan);
//
//    TextResponse getStartSponsorMessage();
//
//    List<StartFinishInformationDto> getCurrentmarketsInfo();
//
//    void addNewMarketProgram(InformationDto dto);
//
//    List<MarketDto> loadPotencialMarketPrograms();
//
//    void rejectProgram(String title);
//
//    List<InformationDto> getFullCapacityInformation();
//
//    void expandTheStadium(Integer delta);
//
//    List<InformationDto> gelAllIncomes();
//
//    List<String> setTrainingEffects();
//
//    List<String> setFinanceUpdates();
//
//    List<String> setMarketingChanges();
//
//    void setPlayerRecovery();
}
