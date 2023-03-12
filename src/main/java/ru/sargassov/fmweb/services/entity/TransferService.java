package ru.sargassov.fmweb.services.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.comparators.TeamsPlayersComparators;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.converters.PlayerConverter;
import ru.sargassov.fmweb.converters.TeamConverter;
import ru.sargassov.fmweb.dto.player_dtos.IdNamePricePlayerDto;
import ru.sargassov.fmweb.dto.player_dtos.PlayerSoftSkillDto;
import ru.sargassov.fmweb.dto.team_dtos.TeamOnPagePlayersDto;
import ru.sargassov.fmweb.exceptions.TooExpensiveException;
import ru.sargassov.fmweb.exceptions.TransferException;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.spi.intermediate_spi.PlayerIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.TeamIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.entity.TransferServiceSpi;

import javax.transaction.Transactional;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.math.BigDecimal.valueOf;
import static ru.sargassov.fmweb.constants.Constant.DEFAULT_STRATEGY_PLACE;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class TransferService implements TransferServiceSpi {

    private final TeamIntermediateServiceSpi teamIntermediateService;
    private final TeamsPlayersComparators comparators;
    private final TeamConverter teamConverter;
    private final TeamService teamService;
    private final PlayerConverter playerConverter;
    private final PlayerIntermediateServiceSpi playerIntermediateService;
    @Transactional
    @Override
    public TeamOnPagePlayersDto getNameOfOpponentTeam(Integer parameter, Integer delta) {

        var user = UserHolder.user;
        var userTeamId = user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        var teams = teamIntermediateService.findAllByUser(user)
                .stream()
                .sorted(Comparator.comparing(Team::getName))
                .collect(Collectors.toList());

        parameter = defineParameter(teams, parameter, delta, userTeam);

        var teamDto = teamConverter.dtoToTeamOnPagePlayersDto(teams.get(parameter));
        teamDto.setCountParameter(parameter);
        return teamDto;
    }

    @Transactional
    @Override
    public List<PlayerSoftSkillDto> getTenPlayersFromNextTeam(Integer playerParameter, Integer sortParameter, Team team) {
        log.info("TransferService.getTenPlayersFromNextTeam(name)");
        var comparator = comparators.getPlayerComparators().get(sortParameter);
        var nextTenPlayers = team.getNextTenPlayersForTransferList(playerParameter, comparator);
        return nextTenPlayers.stream()
                .map(PlayerConverter::getPlayerSoftSkillDtoFromIntermediateEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlayerSoftSkillDto> getTenPlayersFromNextTeam(Integer deltaPlayerParameter, Integer sortParameter, Long teamId) {
        var team = teamIntermediateService.getById(teamId);
        return getTenPlayersFromNextTeam(deltaPlayerParameter, sortParameter, team);
    }

    private Integer defineParameter(List<Team> teams, Integer parameter, Integer delta, Team userTeam) {
        parameter += delta;
        if (parameter >= teams.size()) {
            parameter = 0;
        }
        if (parameter < 0) {
            parameter = teams.size() - 1;
        }
        if (teams.get(parameter).equals(userTeam) && delta == 1) {
            parameter += 1;
        }
        if (teams.get(parameter).equals(userTeam) && delta == -1) {
            parameter -= 1;
        }
        return parameter;
    }

    @Transactional
    @Override
    public List<IdNamePricePlayerDto> getSellingList() {
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        return userTeam.getPlayerList().stream()
                .map(playerIntermediateService::getIdNamePricePlayerDtoFromPlayer)
                .sorted(Comparator.comparing(IdNamePricePlayerDto::getPrice, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void buyNewPlayer(PlayerSoftSkillDto playerSoftSkillDto) {
        var sellerTeam = teamIntermediateService.getById(playerSoftSkillDto.getTeamId());
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        var userTeamWealth = userTeam.getWealth();
        var purchasedPlayer = sellerTeam.findPlayerById(playerSoftSkillDto.getId());
        if (purchasedPlayer == null) {
            throw new TransferException("Player " + playerSoftSkillDto.getName() + " not found in " + playerSoftSkillDto.getClub());
        }
        if (userTeamWealth.compareTo(purchasedPlayer.getPrice()) < 0) {
            throw new TooExpensiveException("Player " + purchasedPlayer.getName() +
                    " have price = " + purchasedPlayer.getPrice() + " is too expensive for " + userTeam.getName());
        }
        userTeam.setWealth(userTeam.getWealth().subtract(purchasedPlayer.getPrice()));
        userTeam.substractTransferExpenses(purchasedPlayer.getPrice());
        sellerTeam.setWealth(sellerTeam.getWealth().add(purchasedPlayer.getPrice()));
        purchasedPlayer.setStrategyPlace(DEFAULT_STRATEGY_PLACE);
        purchasedPlayer.setCapitan(false);
        purchasedPlayer.setTeam(userTeam);
        userTeam.getPlayerList().add(purchasedPlayer);
        purchasedPlayer.setNumber(playerIntermediateService.guessNumber(purchasedPlayer.getTeam(), purchasedPlayer.getNumber()));

        sellerTeam.getPlayerList().remove(purchasedPlayer);
        teamService.addJuniorToTeam(sellerTeam, purchasedPlayer.getPosition());
        teamIntermediateService.captainAppointment(sellerTeam);
        teamIntermediateService.save(sellerTeam);
        teamIntermediateService.save(userTeam);
    }

    @Transactional
    @Override
    public void sellPlayer(Long id) {
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        var userTeamPlayerList = userTeam.getPlayerList();
        var soldPlayer = userTeam.findPlayerById(id);
        if (soldPlayer == null) {
            throw new TransferException("Player with id #" + id + " not found in " + userTeam.getName());
        }
        if (userTeamPlayerList.size() == 18) {
            throw new TransferException("You can't have less than 18 players in your team");
        }
        teamIntermediateService.removePlayerFromTeam(userTeam, soldPlayer);
        var halfPriceOfPlayer = soldPlayer.getPrice().divide(valueOf(2), RoundingMode.HALF_UP);
        userTeam.setWealth(userTeam.getWealth().add(halfPriceOfPlayer));
        userTeam.addTransferExpenses(halfPriceOfPlayer);
        teamService.addJuniorToTeam(userTeam, soldPlayer.getPosition());
        teamIntermediateService.captainAppointment(userTeam);
        teamIntermediateService.save(userTeam);
    }
}
