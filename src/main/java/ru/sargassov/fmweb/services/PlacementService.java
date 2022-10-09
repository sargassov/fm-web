package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.PlacementConverter;
import ru.sargassov.fmweb.dto.PlacementData;
import ru.sargassov.fmweb.intermediate_entities.Placement;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.entities.PlacementEntity;
import ru.sargassov.fmweb.entity_repositories.PlacementRepository;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_spi.PlacementIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.PlayerIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.TeamIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.PlacementServiceSpi;
import ru.sargassov.fmweb.spi.PlayerServiceSpi;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Data
@Slf4j
public class PlacementService implements PlacementServiceSpi {
    private final PlacementRepository placementRepository;
    private final PlacementIntermediateServiceSpi placementIntermediateService;
    private final PlayerIntermediateServiceSpi playerIntermediateService;
    private final PlacementConverter placementConverter;
    private final TeamIntermediateServiceSpi teamIntermediateService;
    private final PlayerServiceSpi playerService;
    private final UserService userService;

    @Override
    public List<PlacementEntity> findAllPlacements(){
        return placementRepository.findAll();
    }

    public Integer allPlacemntsQuantity(){
        return placementRepository.findAllPlacementsQuantity();
    }

    @Override
    @Transactional
    public void loadPlacements(User user) {
        log.info("PlacementService.loadPlacements");
        setPlacementsForAllTeams(user);
        fillPlacementForAllTeams(user);
    }

    @Override
    public void fillPlacementForAllTeams(User user) {
        teamIntermediateService.fillPlacementForAllTeams(user);
    }

    @Override
    @Transactional
    public void setPlacementsForAllTeams(User user) {
        int quantity = allPlacemntsQuantity();
        teamIntermediateService.findAllByUser(user).forEach(team -> fillPlacement(team, user, quantity));
    }

    @Override
    public void fillPlacement(Team team, User user, int quantity) {
        var selected = (long) (Math.random() * quantity) + 1;
        var optionalCurrentPlacementEntity = placementRepository.findById(selected);
        var savedPlacement = getIntermediateEntityAndSave(optionalCurrentPlacementEntity, team, user);
        team.setPlacement(savedPlacement);
        teamIntermediateService.save(team);
    }

    private Placement getIntermediateEntityAndSave(Optional<PlacementEntity> optionalCurrentPlacementEntity, Team team, User user) {
        if (optionalCurrentPlacementEntity.isPresent()) {
            var placementEntity = optionalCurrentPlacementEntity.get();
            var placement = placementConverter.getIntermediateEntityFromEntity(placementEntity, team, user);
            return placementIntermediateService.save(placement);
        }
        throw new IllegalStateException("No match with current placement");
    }

    public void findByPlacementData(PlacementData placementData, Team userTeam, User user) {
        var title = placementData.getTitle();
        var entity = placementRepository.findByName(title);
        var placement = placementConverter.getIntermediateEntityFromEntity(entity, userTeam, user);
        placementIntermediateService.save(placement);
        userTeam.setPlacement(placement);
        userTeam.resetAllStrategyPlaces();
    }

//    @Override
//    @Transactional
//    public List<Placement> getPlacementsFromPlacementApi(){
//        return placementApi.getPlacementApiList();
//    }

//    @Override
//    @Transactional
//    public PlacementOnPagePlacementsDto getCurrentPlacementInfo() {
//        return placementConverter.getPlacementOnPagePlacementsDtoFromTeam(userService.getUserTeam());
//    }

//    @Override
//    @Transactional
//    public void setNewPlacement(PlacementData placementData) {
//        Placement placement = placementApi.getPlacementByTitle(placementData.getTitle());
//        Team userTeam = userService.getUserTeam();
//        userTeam.setPlacement(placement);
//        userTeam.setTeamPower(0);
//        playerService.resetAllStrategyPlaces(userTeam);
//    }



//    @Override
//    @Transactional
//    public void deletePlayerFromCurrentPlacement(Integer number) {
//        teamService.deletePlayerFromCurrentPlacement(number);
//    }
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