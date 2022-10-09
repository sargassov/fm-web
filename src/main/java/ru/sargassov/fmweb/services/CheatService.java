package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.cheats.Cheat;
import ru.sargassov.fmweb.cheats.CheatApi;
import ru.sargassov.fmweb.constants.TextConstant;
import ru.sargassov.fmweb.dto.text_responses.TextResponse;
import ru.sargassov.fmweb.exceptions.CheatException;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Position;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.spi.CheatServiceSpi;
import ru.sargassov.fmweb.spi.PlayerServiceSpi;
import ru.sargassov.fmweb.spi.UserServiceSpi;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CheatService implements CheatServiceSpi {

    private static final String END_FILE = ".java";
    private static final String BEGIN_FILE = "ru";
    private static final String EMPTY = "";
    private static final String POINT = ".";
    private static final String CHEATS_ADDRESS = "src/main/java/ru/sargassov/fmweb/cheats/model";
    private static final String WRONG_CHEAT_CODE = TextConstant.WRONG_CHEAT_CODE;
    private static final String MESSI_LEONEL = TextConstant.MESSI_LEONEL;
    private static final String ARGENTINA = TextConstant.ARGENTINA;
    private static final String YOUR_CLUB_HAD_ALREADY = TextConstant.YOUR_CLUB_HAD_ALREADY;
    private CheatApi cheatApi;
    private PlayerServiceSpi playerService;
    private UserServiceSpi userService;

    @SneakyThrows
    @Override
    public void constructCheats(User user) {
        List<Path> files = Files.walk(
                Paths.get(CHEATS_ADDRESS))
                .filter(Files::isRegularFile)
                .collect(Collectors.toList()); // TODO дописать

        List<Cheat> cheats = new ArrayList<>();
        for(Path file: files) {
            String s = file.toString();
            String shortAddress = s.substring(s.indexOf(BEGIN_FILE));
            String replaceAddress = shortAddress.replaceAll("\\\\", POINT);
            if (replaceAddress.endsWith(END_FILE)) {;
                String filename = replaceAddress.replaceAll(END_FILE, EMPTY);
                Class clazz = Class.forName(filename);
                Object o = clazz.getConstructor().newInstance();
                if (o instanceof Cheat) {
                    ((Cheat) o).setService(this);
                    cheats.add((Cheat) o);
                }
            }
        }
        cheatApi.constructApi(cheats);
    }

    @Override
    public TextResponse activateCheat(TextResponse cheatInfo) {
        String cheatCode = cheatInfo.getResponse();
        Cheat cheat = cheatApi.findByCode(cheatCode);
        if (Objects.isNull(cheat)) {
            String message = WRONG_CHEAT_CODE;
            log.error(message);
            throw new CheatException(message);
        }

        cheat.activate();
        return new TextResponse(cheat.getDescription());
    }

//    public void leoMessiCheatActivate() {
//        Player player = new Player();
//        player.setName(MESSI_LEONEL);
//        player.setNatio(ARGENTINA);
//        player.setGkAble(10);
//        player.setDefAble(15);
//        player.setMidAble(94);
//        player.setForwAble(98);
//        player.setCaptainAble(89);
//        player.setStrategyPlace(-100);
//        player.setBirthYear(1987);
//        player.setPosition(Position.FORWARD);
//        player.guessPower();
//        playerService.guessPrice(player);
//
//        Team userTeam = userService.getUserTeam();
//        if (userTeam.isPlayerExists(player.getName())) {
//            throw new CheatException(YOUR_CLUB_HAD_ALREADY + MESSI_LEONEL);
//        }
//        userTeam.getPlayerList().add(player);
//        player.setTeam(userTeam);
//        player.guessNumber(10);
//        player.setTrainingAble(10);
//    }

//    public void noInjuriesCheatActivate() {
//        Team userTeam = userService.getUserTeam();
//        List<Player> playerList = userTeam.getPlayerList();
//        for (Player player : playerList) {
//            player.setInjury(false);
//        }
//    }
//
//    public void tenMillionEuroCheat() {
//        Team userTeam = userService.getUserTeam();
//        BigDecimal wealthBefore = userTeam.getWealth();
//        userTeam.setWealth(wealthBefore.add(BigDecimal.valueOf(10)));
//    }
}
