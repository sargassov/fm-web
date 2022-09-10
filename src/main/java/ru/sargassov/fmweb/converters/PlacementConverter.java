package ru.sargassov.fmweb.converters;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.constants.Constant;
import ru.sargassov.fmweb.dto.PlacementOnPagePlacementsDto;
import ru.sargassov.fmweb.dto.player_dtos.PlayerOnPagePlacementsDto;
import ru.sargassov.fmweb.intermediate_entities.*;
import ru.sargassov.fmweb.entities.PlacementEntity;
import ru.sargassov.fmweb.intermediate_spi.RoleIntermediateServiceSpi;

import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class PlacementConverter {
    private PlayerConverter playerConverter;
    private RoleIntermediateServiceSpi roleIntermediateService;

    public Placement getIntermediateEntityFromEntity(PlacementEntity placementEntity, Team team, User user){
        var placement = new Placement();
        placement.setName(placementEntity.getName());
        placement.setRoles(roleUnpacker(placementEntity.getRoles(), user, team));
        placement.setUser(user);
        placement.setTeam(team);
        return placement;
    }

    private List<Role> roleUnpacker(String roles, User user, Team team) {
        var splitter = ",";
        var rolesSplit = roles.split(splitter);
        var listRoles = new ArrayList<Role>();

        for(int x = 0; x < rolesSplit.length; x++){
            rolesSplit[x] = purePlacementName(rolesSplit[x]);
            var role = getRoleIntermediateEntityFromString(rolesSplit[x], x, user, team);
            listRoles.add(role);
        }
        return listRoles;
    }

    private Role getRoleIntermediateEntityFromString(String roleName, int count, User user, Team team) {
        var role = new Role();
        role.setTitle(roleName);
        role.setPosNumber(count);
        role.setUser(user);
        role.setTeam(team);
        return roleIntermediateService.save(role);
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
                dtoList.add(playerConverter.getPlayerHardSkillDtoFromIntermediateEntity(opt.get()));
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
