package ru.sargassov.fmweb.constants;

import ru.sargassov.fmweb.intermediate_entites.Team;

public class TextConstant {

    public static String getBanksStartMessage(Team userTeam, int banksValue){
        if(banksValue == 0) return userTeam.getName() + " haven't loans yet." +
                " You can take any Loan.";
        else if(banksValue < Team.maxValueOfLoans) return userTeam.getName() +
                " have " + banksValue + " loans now. You can take not more than " +
                (Team.maxValueOfLoans - banksValue) + ".";
        else return userTeam.getName() +
                    " have max value of loans. You can't take more!";
    }
}
