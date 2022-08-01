package ru.sargassov.fmweb.constants;

import ru.sargassov.fmweb.intermediate_entites.Team;

public final class TextConstant {

    public static String getBanksStartMessage(Team userTeam, int banksValue){
        if(banksValue == 0) return userTeam.getName() + " haven't loans yet." +
                " You can take any Loan.";
        else if(banksValue < Team.maxValueOfLoans) return userTeam.getName() +
                " have " + banksValue + " loans now. You can take not more than " +
                (Team.maxValueOfLoans - banksValue) + ".";
        else return userTeam.getName() +
                    " have max value of loans. You can't take more!";
    }

    public static String permissionToChangeSponsor(boolean permission) {
        if(permission) {
            return "You have already changed your sponsor. You can't do it ones again!";
        } else {
            return "You can choose a new sponsor from the list.";
        }
    }
}
