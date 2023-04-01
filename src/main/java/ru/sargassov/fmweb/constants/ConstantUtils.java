package ru.sargassov.fmweb.constants;

import ru.sargassov.fmweb.intermediate_entities.Day;
import ru.sargassov.fmweb.intermediate_entities.Player;

import java.time.Month;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class ConstantUtils {

    private static final String ILLEGAL_MONTH = "Illegal month";
    private static final String SPACE = " ";

    public static Integer getActualMonthValue(Day actualDay) {
        var months = Constant.Month.values();
        var actualMonth = actualDay.getDate().getMonth();
        for (var m : months) {
            if (actualMonth.toString().equals(m.toString())) {
                return m.ordinal();
            }
        }
        throw new IllegalStateException(ILLEGAL_MONTH);
    }

    public static Month getMonthByParameter(Integer parameter) {



        var months = Constant.Month.values();
        var currentMonth = months[parameter].toString();
        var monthStringsMap = new LinkedHashMap<String, Month>();
        Arrays.stream(Month.values())
                .forEach(month -> monthStringsMap.put(month.toString(), month));
        for (var m : monthStringsMap.entrySet()) {
            if (currentMonth.equals(m.getKey())) {
                return m.getValue();
            }
        }
        throw new IllegalStateException(ILLEGAL_MONTH);
    }

    public static String preMatchPlayerFormUtil(Player player) {
        if (player == null) {
            return "N/N";
        }
        var playerInfo = player.getNumber() + "." + SPACE +
                player.getPosition().getDescription().charAt(0) + SPACE +
                player.getName() + SPACE + "[" + player.getPower() + "]";
        if (player.isCapitan()) {
            playerInfo += SPACE + "(C)";
        }
        return playerInfo;
    }

    public static int monthsGetint(String month) {
        var values = Month.values();
        for (var m : values) {
            if (m.toString().equals(month)) {
                return m.getValue();
            }
        }
        throw new IllegalStateException("Illegal month");
    }
}
