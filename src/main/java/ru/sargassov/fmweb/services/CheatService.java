package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.cheats.Cheat;
import ru.sargassov.fmweb.cheats.CheatApi;
import ru.sargassov.fmweb.constants.TextConstant;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.dto.text_responses.TextResponse;
import ru.sargassov.fmweb.enums.PositionType;
import ru.sargassov.fmweb.exceptions.CheatException;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.spi.CheatServiceSpi;
import ru.sargassov.fmweb.spi.PlayerServiceSpi;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.sargassov.fmweb.constants.Constant.DEFAULT_STRATEGY_PLACE;

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

    @SneakyThrows
    @Override
    public void constructCheats(User user) {
        var files = Files.walk(
                Paths.get(CHEATS_ADDRESS))
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());

        var cheats = new ArrayList<Cheat>();
        for(Path file: files) {
            var s = file.toString();
            var shortAddress = s.substring(s.indexOf(BEGIN_FILE));
            var replaceAddress = shortAddress.replaceAll("\\\\", POINT);
            if (replaceAddress.endsWith(END_FILE)) {;
                var filename = replaceAddress.replaceAll(END_FILE, EMPTY);
                var clazz = Class.forName(filename);
                var o = clazz.getConstructor().newInstance();
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
        var cheatCode = cheatInfo.getResponse();
        var cheat = cheatApi.findByCode(cheatCode);
        if (Objects.isNull(cheat)) {
            String message = WRONG_CHEAT_CODE;
            log.error(message);
            throw new CheatException(message);
        }

        cheat.activate();
        return new TextResponse(cheat.getDescription());
    }

    @Transactional
    public void leoMessiCheatActivate() {
        var player = new Player();
        var user = UserHolder.user;
        player.setName(MESSI_LEONEL);
        player.setNatio(ARGENTINA);
        player.setGkAble(10);
        player.setDefAble(15);
        player.setMidAble(94);
        player.setForwAble(98);
        player.setCaptainAble(89);
        player.setStrategyPlace(DEFAULT_STRATEGY_PLACE);
        player.setBirthYear(1987);
        player.setPosition(PositionType.FORWARD);
        player.guessPower();
        playerService.guessPrice(player, user);

        var userTeam = user.getUserTeam();
        if (userTeam.isPlayerExists(player.getName())) {
            throw new CheatException(YOUR_CLUB_HAD_ALREADY + MESSI_LEONEL);
        }
        userTeam.getPlayerList().add(player);
        player.setTeam(userTeam);
        player.setNumber(10);
        player.guessNumber(10);
        player.setTrainingAble(10);
        player.setTimeBeforeTreat(0);
        player.setTire(0);
    }

    @Transactional
    public void noInjuriesCheatActivate() {
        var userTeam = UserHolder.user.getUserTeam();
        var playerList = userTeam.getPlayerList();
        for (Player player : playerList) {
            player.setInjury(false);
        }
    }

    @Transactional
    public void tenMillionEuroCheat() {
        var userTeam = UserHolder.user.getUserTeam();
        BigDecimal wealthBefore = userTeam.getWealth();
        userTeam.setWealth(wealthBefore.add(BigDecimal.valueOf(10)));
    }
}
