package ru.sargassov.fmweb.constants;

import ru.sargassov.fmweb.dto.InformationDto;
import ru.sargassov.fmweb.exceptions.StadiumException;
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
    private static final String FULL_SECTOR_CAPACITY = "Full Sector Capacity";
    private static final String FULL_UNDIVIDED_CAPACITY = "Full Undivided Capacity";
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

    public static List<InformationDto> getCurrentStadiumStatus(Team userTeam) {

        Stadium stadium = userTeam.getStadium();

        return new ArrayList<>(List.of(
                new InformationDto(FULL_STADIUM_CAPACITY, stadium.getFullCapacity()),
                new InformationDto(FULL_SECTOR_CAPACITY, stadium.getFullSectorCapacity()),
                new InformationDto(FULL_UNDIVIDED_CAPACITY, stadium.getFullCapacity() - stadium.getFullSectorCapacity()),
                new InformationDto(USUAL_AVERAGE_CAPACITY, stadium.getUsualAverageCapacity())
        ));
    }

    public static List<InformationDto> getTicketCostInfo(Team userTeam) {
        Stadium stadium = userTeam.getStadium();
        return new ArrayList<>(List.of(
                new InformationDto(SIMPLE_TICKET_COST, milliomMulty(stadium.getSimpleTicketCost())),
                new InformationDto(FAN_TICKET_COST, milliomMulty(stadium.getFanTicketCost())),
                new InformationDto(FAMILY_TICKET_COST, milliomMulty(stadium.getFamilyTicketCost())),
                new InformationDto(VIP_TICKET_COST, milliomMulty(stadium.getVipTicketCost())),
                new InformationDto(AWAY_TICKET_COST, milliomMulty(stadium.getAwayTicketCost()))
        ));
    }

    public static BigDecimal getCostByTypeOfSector(Stadium stadium, String type) {
        if(type.equals(SIMPLE_TICKET_COST)) return stadium.getSimpleTicketCost();
        if(type.equals(FAMILY_TICKET_COST)) return stadium.getFamilyTicketCost();
        if(type.equals(FAN_TICKET_COST)) return stadium.getFanTicketCost();
        if(type.equals(VIP_TICKET_COST)) return stadium.getVipTicketCost();
        if(type.equals(AWAY_TICKET_COST)) return stadium.getAwayTicketCost();

        else {
            throw new StadiumException("Got unreadable ticket cost parameter!");
        }
    }

    public static List<InformationDto> getSplitSectorsInfo(Team userTeam) {
        Stadium stadium = userTeam.getStadium();
        return new ArrayList<>(List.of(
                new InformationDto(FULL_STADIUM_CAPACITY, stadium.getFullCapacity()),
                new InformationDto(FULL_SECTOR_CAPACITY, stadium.getFullSectorCapacity()),
                new InformationDto(FULL_UNDIVIDED_CAPACITY, stadium.getFullCapacity() - stadium.getFullSectorCapacity())

        ));
    }

    public static List<InformationDto> getSectorsCapacityInfo(Team userTeam) {
        Stadium stadium = userTeam.getStadium();
        return new ArrayList<>(List.of(
                new InformationDto(SIMPLE_CAPACITY, stadium.getSimpleCapacity()),
                new InformationDto(FAMILY_CAPACITY, stadium.getFamilyCapacity()),
                new InformationDto(FAN_CAPACITY, stadium.getFanCapacity()),
                new InformationDto(VIP_CAPACITY, stadium.getVipCapacity()),
                new InformationDto(AWAY_CAPACITY, stadium.getAwayCapacity())
        ));
    }
}
