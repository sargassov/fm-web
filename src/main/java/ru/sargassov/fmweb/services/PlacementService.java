package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api.PlacementApi;
import ru.sargassov.fmweb.converters.PlacementConverter;
import ru.sargassov.fmweb.dto.LeagueDto;
import ru.sargassov.fmweb.dto.TeamDto;
import ru.sargassov.fmweb.entities.Placement;
import ru.sargassov.fmweb.repositories.PlacementRepository;

import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
@Data
public class PlacementService {
    private final PlacementRepository placementRepository;
    private final PlacementApi placementApi;
    private final PlacementConverter placementConverter;
    private final LeagueDto leagueDto;

    public List<Placement> findAllPlacements(){
        return placementRepository.findAll();
    }

    public void createPlacementApi() {
        findAllPlacements().stream()
        .map(placementConverter::entityToDto)
        .forEach(placementApi::addPlacementDto);
    }

    public void setPlacementsForAllTeams() {
        leagueDto.getTeamList().forEach(this::fillPlacement);
    }

    public void fillPlacement(TeamDto teamDto) {
        teamDto.setPlacementDto(
                placementApi.getPlacementDto(new Random()
                        .nextInt(placementApi.getSize())));

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