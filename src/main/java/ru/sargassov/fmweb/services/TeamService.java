package ru.sargassov.fmweb.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api.TeamApi;
import ru.sargassov.fmweb.api.UserApi;
import ru.sargassov.fmweb.comparators.TeamsPlayersComparators;
import ru.sargassov.fmweb.converters.TeamConverter;
import ru.sargassov.fmweb.dto.*;
import ru.sargassov.fmweb.exceptions.PlayerNotFoundException;
import ru.sargassov.fmweb.intermediate_entites.*;
import ru.sargassov.fmweb.repositories.TeamRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamConverter teamConverter;
    private final PlayerService playerService;
    private final JuniorService juniorService;
    private final TeamApi teamApi;
    private final UserApi userApi;
    private final TeamsPlayersComparators teamsPlayersComparators;

    public void loadTeams(){
        teamApi.setTeamApiList(findAll());
        fillTeams(teamApi.getTeamApiList());
        juniorRecruitment(teamApi.getTeamApiList());
    }

    public List<Team> findAll(){
        log.info("TeamService.getAllTeams");
        return teamRepository.findAll().stream()
                .map(teamConverter::entityToDto).collect(Collectors.toList());
    }

//    public TeamDto getTeamInLeagueDtoById(Long id) {
//        log.info("TeamService.getTeamInLeagueDtoById");
//        return leagueDto.getTeamList().stream()
//                .filter(t -> t.getId() == id).findFirst().orElseThrow(()
//                -> new RuntimeException("Team with id = " + id + " wasn't found"));
//    }

    public void fillTeams(List<Team> teamList) {
        log.info("TeamService.fillTeams");
        teamList.forEach(t -> {
            t.setPlayerList(playerService.getAllPlayersByTeamId(t.getId()));
        });
    }

    public void juniorRecruitment(List<Team> teamList) {
        log.info("TeamService.juniorRecruitment");
        int maxValueOfYoungPlayersForOnePosition = 2;

        for(Team currentTeam : teamList){
            for(Position currentPosition : Position.values()){
                for (int i = 0; i < maxValueOfYoungPlayersForOnePosition; i++) {
                    Player player = juniorService.getYoungPlayer(currentPosition);
                    player.setTeam(currentTeam);
                    player.setNumber(randomGuessNum(currentTeam));
                    currentTeam.getPlayerList().add(player);
                }
            }
        }
    }

    private int randomGuessNum(Team currentTeam) {
        int num = 99;
        List<Integer> ints = currentTeam.getPlayerList()
                .stream().map(Player::getNumber)
                .collect(Collectors.toList());

        while (true){
            if(ints.contains(num))
                num--;
            else
                break;
        }
        return num;
    }

    public List<Team> getTeamListFromApi() {
        return teamApi.getTeamApiList();
    }

    public Team getTeamByIdFromApi(Long id){
        return teamApi.findTeamById(id);
    }

    public Team getTeamByNameFromApi(String name){
        return teamApi.findByName(name);
    }

    public void fillPlacementForAllTeams() {
        getTeamListFromApi().stream()
                .forEach(t -> {
                    autoFillPlacement(t);
                    captainAppointment(t);
                    powerTeamCounter(t);
                });
    }

    public void autoFillPlacement(Team t) {
        log.info("TeamService.autoFillPlacement for " + t.getName());

        List<Player> playerList = t.getPlayerList().stream()
                .filter(p -> !p.isInjury())
                .collect(Collectors.toList()); // только здоровые игроки

        t.getPlacement().getRoles().forEach(role -> {
            log.info(t.getName() + " " + role.getTitle());
            List<Player> suitablePlayers = getSuitablePlayers(playerList, role).stream()
                    .filter(p -> p.getStrategyPlace() == -100)
                    .collect(Collectors.toList()); // подходящие игроки на конкретную позицию

            Player selected = (findBest(suitablePlayers));
            selected.setFirstEighteen(true);
            selected.setStrategyPlace(role.getPosNumber());
        });
    }

    private Player findBest(List<Player> suitablePlayers) {
        Player best = suitablePlayers.stream().sorted((o1, o2) ->
                Integer.compare(o2.getPower(), o1.getPower()))
                .limit(1)
                .findFirst()
                .orElseThrow(() ->
                        new PlayerNotFoundException("Player in method TeamService.findBest() not found"));
        log.info(best.getName());
        return best;
    }

    private List<Player> getSuitablePlayers(List<Player> playerList, Role role) {
        List<Player> pl =  playerList.stream()
                .filter(p -> p.equalsPosition(role))
                .collect(Collectors.toList());
        return pl;
    }

    private void captainAppointment(Team team) {
        Player player = team.getPlayerList().stream().sorted((o1, o2) -> Integer.compare(o2.getCaptainAble(), o1.getCaptainAble()))
                .limit(1)
                .findFirst()
                .orElseThrow(() ->
                        new PlayerNotFoundException("Player in method TeamService.captainAppointment() not found"));

        player.setCapitan(true);
    }

    public void setNewCaptainHandle(String name){
        Player nowCap = null, futureCap;
        try{
            nowCap = userApi.getCaptainOfUserTeam();
        }
        catch (RuntimeException r) {
            log.error("Captain function is not avalible");
        }
        futureCap = userApi.getPlayerByNameFromUserTeam(name);

        nowCap.setCapitan(false);
        futureCap.setCapitan(true);
    }

    public void powerTeamCounter(Team team) {
        int power = 0;

        List<Player> playerList = team.getPlayerList().stream()
                .filter(p -> p.getStrategyPlace() > -1 && p.getStrategyPlace() < 11)
                .collect(Collectors.toList());

        for(Player p: playerList){
            power += p.getPower();
            if(p.isCapitan())
                power += p.getCaptainAble();
        }
        team.setTeamPower(power / 11);
    }
    ///////////////////////////////////////////////////////////////стартовые методы


    public TeamOnPagePlayersDto getNameOfUserTeam() {
        return teamConverter.dtoToTeamOnPagePlayersDto(userApi.getTeam());
    }

    public List<PlayerOnPagePlayersDto> getAllPlayersByUserTeam(Integer parameter) {
        log.info("TeamService.getAllPlayersByUserTeam()");
        List<Player> players = userApi.getUserTeam().getPlayerList();
        List<PlayerOnPagePlayersDto> playerOnPagePlayersDtos = playerService.getPlayerOnPagePlayersDtoFromPlayer(players);

        playerOnPagePlayersDtos.sort(teamsPlayersComparators.getComparators().get(parameter));
        return playerOnPagePlayersDtos;
    }


    public void deletePlayerFromCurrentPlacement(Integer number) {
        Team team = userApi.getTeam();
        Player player = userApi.getPlayerByNumberFromUserTeam(number);
        player.setStrategyPlace(-100);
        powerTeamCounter(team);
    }
//    //////////////////////////////////////////////////////////////////////////////

}
