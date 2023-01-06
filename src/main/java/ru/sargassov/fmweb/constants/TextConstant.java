package ru.sargassov.fmweb.constants;

import ru.sargassov.fmweb.dto.text_responses.TextResponse;
import ru.sargassov.fmweb.intermediate_entities.Team;

import java.util.ArrayList;
import java.util.List;

public final class TextConstant {

    public static final String WRONG_CHEAT_CODE = "Wrong cheat code!";
    public static final String MESSI_LEONEL = "Messi Leonel";
    public static final String ARGENTINA = "Arg";
    public static final String YOUR_CLUB_HAD_ALREADY = "Your club had already ";
    public static final String MATCH_DONT_PLAYED = "Your club don't played match today";
    public static final String DASH_DELIVER = "-";

    public static String getBanksStartMessage(Team userTeam, int banksValue){
        if(banksValue == 0) return userTeam.getName() + " haven't loans yet." +
                " You can take any Loan.";
        else if(banksValue < Team.MAX_VALUE_OF_LOANS) return userTeam.getName() +
                " have " + banksValue + " loans now. You can take not more than " +
                (Team.MAX_VALUE_OF_LOANS - banksValue) + ".";
        else return userTeam.getName() +
                    " have max value of loans. You can't take more!";
    }

    public static String permissionToChangeSponsor(boolean permission) {
        if (permission) {
            return "You have already changed your sponsor. You can't do it ones again!";
        } else {
            return "You can choose a new sponsor from the list.";
        }
    }

    public static List<TextResponse> dropStringsIntoTextResponses(List<String> strings) {
        var responses = new ArrayList<TextResponse>();
        for (var s : strings) {
            responses.add(new TextResponse(s));
        }
        return responses;
    }
}
