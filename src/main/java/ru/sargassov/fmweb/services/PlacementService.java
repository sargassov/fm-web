package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api_temporary_classes_group.PlacementApi;
import ru.sargassov.fmweb.converters.PlacementConverter;
import ru.sargassov.fmweb.dto.PlacementData;
import ru.sargassov.fmweb.dto.PlacementOnPagePlacementsDto;
import ru.sargassov.fmweb.intermediate_entites.League;
import ru.sargassov.fmweb.intermediate_entites.Placement;
import ru.sargassov.fmweb.intermediate_entites.Team;
import ru.sargassov.fmweb.entities.PlacementEntity;
import ru.sargassov.fmweb.repositories.PlacementRepository;
import ru.sargassov.fmweb.spi.PlacementServiceSpi;
import ru.sargassov.fmweb.spi.PlayerServiceSpi;
import ru.sargassov.fmweb.spi.TeamServiceSpi;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Data
@Slf4j
public class PlacementService implements PlacementServiceSpi {
    private final PlacementRepository placementRepository;
    private final PlacementApi placementApi;
    private final PlacementConverter placementConverter;
    private final TeamServiceSpi teamService;
    private final PlayerServiceSpi playerService;
    private final League league;
    private final UserService userService;

    @Override
    @Transactional
    public List<PlacementEntity> findAllPlacements(){
        return placementRepository.findAll();
    }

    @Override
    @Transactional
    public void loadPlacements() {
        log.info("PlacementService.loadPlacements");
        placementApi.setPlacementApiList(findAllPlacements().stream()
        .map(placementConverter::entityToDto)
        .collect(Collectors.toList()));

        setPlacementsForAllTeams();
        fillPlacementForAllTeams();
        //установить игроков для каждой расстановки
    }

    @Override
    @Transactional
    public void fillPlacementForAllTeams() {
        teamService.fillPlacementForAllTeams();
    }

    @Override
    @Transactional
    public void setPlacementsForAllTeams() {
        teamService.getTeamListFromApi().forEach(this::fillPlacement);
    }

    @Override
    @Transactional
    public void fillPlacement(Team team) {
        int selected = (int) (Math.random() * placementApi.getPlacementApiList().size());
        team.setPlacement(placementApi.getPlacementByNumber(selected));
    }

    @Override
    @Transactional
    public List<Placement> getPlacementsFromPlacementApi(){
        return placementApi.getPlacementApiList();
    }

    @Override
    @Transactional
    public PlacementOnPagePlacementsDto getCurrentPlacementInfo() {
        return placementConverter.getPlacementOnPagePlacementsDtoFromTeam(userService.getUserTeam());
    }

    @Override
    @Transactional
    public void setNewPlacement(PlacementData placementData) {
        Placement placement = placementApi.getPlacementByTitle(placementData.getTitle());
        Team userTeam = userService.getUserTeam();
        userTeam.setPlacement(placement);
        userTeam.setTeamPower(0);
        playerService.resetAllStrategyPlaces(userTeam);
    }

    @Override
    @Transactional
    public void autoFillCurrentPlacement() {
        Team userTeam = userService.getUserTeam();
        teamService.autoFillPlacement(userTeam);
        teamService.powerTeamCounter(userTeam);
    }

    @Override
    @Transactional
    public void deletePlayerFromCurrentPlacement(Integer number) {
        teamService.deletePlayerFromCurrentPlacement(number);
    }
}


//    public static void playerRandomizer(Team team){
//
//        System.out.println("player randomizer " + team.getName());
//        List<Role> roleList = getFreePlacesInPlacement(team); // осатлись только не занятые позиции в расстановке
//        List<Player> playerList = getHealtyPlayers(team); // только здоровые игроки не зdнятые в текущей расстановке
//
//        roleList.forEach(role -> {
//            List<Player> suitablePlayers = getSuitablePlayers(playerList, role); // подходящие игроки на конкретную позицию
//
//            Player selected = (findBest(suitablePlayers));
//            selected.setFirstEleven(true);
//            selected.setStrategyPlace(role.getPosNumber());
//            role.setPlayer(selected);
//        });
//
//        captainAppointment(team);
//        team.setTeamPower(powerTeamCounter(team));
//
//        System.out.println(team.getName() + " have power = " + team.getTeamPower());
//    }
//
//    private static void captainAppointment(Team team) {
//        Player player = team.getPlayerList().stream().sorted(new Comparator<Player>() {
//            @Override
//            public int compare(Player o1, Player o2) {
//                return Integer.compare(o2.getCaptainAble(), o1.getCaptainAble());
//            }
//        }).limit(1).findFirst().get();
//
//        player.setCapitan(true);
//        System.out.println(player.getName());
//    }
//
//    private static List<Player> getSuitablePlayers(List<Player> playerList, Role role) {
//
//        return playerList.stream()
//                .filter(p -> p.getStrategyPlace() < 0 && p.getPosition().equals(role.getPosition()))
//                .collect(Collectors.toList());
//
//    }
//
//    private static List<Player> getHealtyPlayers(Team team) {
//
//        return team.getPlayerList()
//                .stream().filter(p -> !(p.isPlayerInjury()))
//                .collect(Collectors.toList());
//    }
//
//    private static List<Role> getFreePlacesInPlacement(Team team) {
//        return team.getPlacement().getRoleList()
//                .stream().filter(r -> r.getPlayer() == null).collect(Collectors.toList());
//    }
//
//    private static Player findBest(List<Player> suitablePlayers) { //найти лучшего по силе игрока из списка
//        return suitablePlayers.stream().sorted((o1, o2) ->
//                Integer.compare(o2.getPower(), o1.getPower()))
//                .limit(1).findFirst().get();
//    }
//
//    public static int powerTeamCounter(Team team) {
//        int power = 0;
//
//        List<Player> playerList = team.getPlayerList().stream()
//                .filter(p -> p.getStrategyPlace() > -1 && p.getStrategyPlace() < 11)
//                .collect(Collectors.toList());
//
//        for(Player p: playerList){
//            power += p.getPower();
//            if(p.isCapitan())
//                power += p.getCaptainAble();
//        }
//
//        return power / 11;
//    }