package ru.sargassov.fmweb.constants;

import ru.sargassov.fmweb.dto.InformationDto;
import ru.sargassov.fmweb.intermediate_entites.Stadium;
import ru.sargassov.fmweb.intermediate_entites.Team;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class StadiumAnalytics {

    private static final String TEAM_TITLE = "Team Title";
    private static final String STADIUM_TITLE = "Stadium Title ";
    private static final String FULL_STADIUM_CAPACITY = "Full Stadium Capacity";
    private static final String SIMPLE_CAPACITY = "Simple Capacity";
    private static final String SIMPLE_TICKET_COST = "Simple Ticket Cost";
    private static final String FAMILY_CAPACITY = "Family Capacity";
    private static final String FAMILY_TICKET_COST = "Family Ticket Cost";
    private static final String FAN_CAPACITY  = "Fan Capacity ";
    private static final String FAN_TICKET_COST = "Fan Ticket Cost";
    private static final String AWAY_CAPACITY = "Away Capacity";
    private static final String AWAY_TICKET_COST = "Away Ticket Cost";
    private static final String VIP_CAPACITY = "VIP Capacity";
    private static final String VIP_TICKET_COST = "VIP Ticket Cost";
    private static final String USUAL_AVERAGE_CAPACITY = "Usual Average Capacity";
    private static final String MATCH_TICKET_REVENUE = "Match Ticket Revenue ";
    private static final String TOTAL_STADIUM_EXPENSES = "Total Stadium Expenses";



    public static List<InformationDto> getStadiumInforamtion(Team userTeam) {

        Stadium stadium = userTeam.getStadium();

        return new ArrayList<>(List.of(
                new InformationDto(TEAM_TITLE, userTeam.getName()),
                new InformationDto(STADIUM_TITLE, stadium.getTitle()),
                new InformationDto(FULL_STADIUM_CAPACITY, stadium.getFullCapacity()),
                new InformationDto(SIMPLE_CAPACITY, stadium.getSimpleCapacity()),
                new InformationDto(SIMPLE_TICKET_COST, milliomMulty(stadium.getSimpleTicketCost())),
                new InformationDto(FAMILY_CAPACITY, stadium.getFamilyCapacity()),
                new InformationDto(FAMILY_TICKET_COST, milliomMulty(stadium.getFamilyTicketCost())),
                new InformationDto(FAN_CAPACITY, stadium.getFanCapacity()),
                new InformationDto(FAN_TICKET_COST, milliomMulty(stadium.getFanTicketCost())),
                new InformationDto(AWAY_CAPACITY, stadium.getAwayCapacity()),
                new InformationDto(AWAY_TICKET_COST, milliomMulty(stadium.getAwayTicketCost())),
                new InformationDto(VIP_CAPACITY, stadium.getVipCapacity()),
                new InformationDto(VIP_TICKET_COST, milliomMulty(stadium.getVipTicketCost())),
                new InformationDto(USUAL_AVERAGE_CAPACITY, stadium.getUsualAverageCapacity()),
                new InformationDto(MATCH_TICKET_REVENUE, milliomMulty(stadium.getMatchTicketRevenue())),
                new InformationDto(TOTAL_STADIUM_EXPENSES, userTeam.getStadiumExpenses())
        ));
    }

    private static String milliomMulty(BigDecimal bigDecimal){
        bigDecimal = bigDecimal.multiply(BigDecimal.valueOf(1_000_000));
        return bigDecimal.setScale(2, RoundingMode.HALF_UP) + " $";
    }
}
