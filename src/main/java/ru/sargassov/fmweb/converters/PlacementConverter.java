package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.constants.Constant;
import ru.sargassov.fmweb.dto.PlacementOnPagePlacementsDto;
import ru.sargassov.fmweb.dto.PlayerOnPagePlacementsDto;
import ru.sargassov.fmweb.intermediate_entites.Placement;
import ru.sargassov.fmweb.intermediate_entites.Player;
import ru.sargassov.fmweb.intermediate_entites.Role;
import ru.sargassov.fmweb.entities.PlacementEntity;
import ru.sargassov.fmweb.intermediate_entites.Team;

import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class PlacementConverter {
    private PlayerConverter playerConverter;

    public Placement entityToDto(PlacementEntity placementEntity){
        Placement pDto = new Placement();
        pDto.setId(placementEntity.getId());
        pDto.setName(placementEntity.getName());
        pDto.setRoles(roleUnpacker(placementEntity.getRoles()));
        return pDto;
    }

    private List<Role> roleUnpacker(String roles) {
        String splitter = ",";
        String[] rolesSplit = roles.split(splitter);
        List<Role> dtoRoles = new ArrayList<>();

        for(int x = 0; x < rolesSplit.length; x++){
            rolesSplit[x] = purePlacementName(rolesSplit[x]);
            dtoRoles.add(new Role(rolesSplit[x], x));
        }
        return dtoRoles;
    }

    private String purePlacementName(String s) {
        s = s.replace("{", "");
        s = s.replace("}", "");
        return s;
    }

//    /////////////////////////////////////////////////////////////////////////////

    public PlacementOnPagePlacementsDto getPlacementOnPagePlacementsDtoFromTeam(Team userTeam) {
        PlacementOnPagePlacementsDto pOnPagePlDto = new PlacementOnPagePlacementsDto();
        pOnPagePlDto.setTitle(userTeam.getPlacement().getName());
        pOnPagePlDto.setCurrentTeamPower(userTeam.getTeamPower());
        pOnPagePlDto.setSize(0);
        pOnPagePlDto.setPlayers(setPlayersDtoForPlacement(userTeam, pOnPagePlDto));


//                userTeam.getPlayerList().
//                        stream()
//                        .filter(p -> p.getStrategyPlace() >= 0)
//                        .map(p -> playerConverter.getPlayerOnPagePlacementsDtoFromPlayer(p))
//                        .sorted(Comparator.comparing(PlayerOnPagePlacementsDto::getStrategyPlace))
//                        .collect(Collectors.toList()));

        return pOnPagePlDto;
    }

    private List<PlayerOnPagePlacementsDto> setPlayersDtoForPlacement(Team userTeam, PlacementOnPagePlacementsDto pOnPagePlDto) {
        List<PlayerOnPagePlacementsDto> dtoList = new ArrayList<>(Constant.placementSize);
        List<Player> playerList = userTeam.getPlayerList().stream()
                        .filter(p -> p.getStrategyPlace() >= 0)
                        .sorted(Comparator.comparing(Player::getStrategyPlace))
                        .collect(Collectors.toList());

        for (int i = 0; i < Constant.placementSize; i++) {
            final int finalI = i;
            Optional<Player> opt = playerList.stream().filter(p -> p.getStrategyPlace() == finalI).findFirst();
            if(opt.isPresent()){
                dtoList.add(playerConverter.getPlayerOnPagePlacementsDtoFromPlayer(opt.get()));
                pOnPagePlDto.setSize(pOnPagePlDto.getSize() + 1);
            }
            else {
                String role = userTeam.getPlacement().getRoles().get(i).getTitle().toUpperCase(Locale.ROOT);
                dtoList.add(new PlayerOnPagePlacementsDto("", 0, 0, role, 0));
            }
        }
        return dtoList;
    }
}
