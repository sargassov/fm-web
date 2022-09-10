package ru.sargassov.fmweb.constants;

import ru.sargassov.fmweb.dto.text_responses.InformationDto;
import ru.sargassov.fmweb.intermediate_entities.Sponsor;
import ru.sargassov.fmweb.intermediate_entities.Team;

import java.util.ArrayList;
import java.util.List;


public final class FinanceAnalytics {
    private static final String TOTAL_BUDGET = "Total Budget";
    private static final String ALL_PLAYERS_COST = "All Players Cost";
    private static final String TEAM_SPONSOR = "Team's Sponsor";
    private static final String TEAM_DAY_WAGE = "Team's Day Wage";
    private static final String TEAM_MATCH_WAGE = "Team's Match Wage";
    private static final String TEAM_GOAL_WAGE = "Team's Goal Wage";
    private static final String TEAM_WIN_WAGE = "Team's Win Wage";
    private static final String TEAM_DRAW_WAGE = "Team's Draw Wage";
    private static final String PROFICIT_DEFICIT = "Proficit(+)/Deficit(-)";
    private static final String LOANS_VALUE = "Team's Value Of Loans";
    private static final String TEAM_DAILY_EXPENSES = "Team's Daily expenses";
    private static final String TEAM_WEEKLY_EXPENSES = "Team's Weekly expenses";
    private static final String TEAM_MONTHLY_EXPENSES = "Team's Monthly expenses";
    private static final String TEAM_TRANSFER_EXPENSES = "Team's Transfer expenses";
    private static final String TEAM_PERSONAL_EXPENSES = "Team's Personal expenses";
    private static final String TEAM_STADIUM_RENEWED_EXPENSES = "Team's Stadium renewed expenses";
    private static final String TEAM_MARKET_EXPENSES = "Team's Market expenses";

    private FinanceAnalytics() {}

    public static List<InformationDto> getIncomes(Team userTeam){
        Sponsor sponsor = userTeam.getSponsor();

        return new ArrayList<>(List.of(
                new InformationDto(TOTAL_BUDGET, userTeam.getWealth()),
                new InformationDto(ALL_PLAYERS_COST, userTeam.getAllPlayersCost()),
                new InformationDto(TEAM_SPONSOR, sponsor.getName()),
                new InformationDto(TEAM_DAY_WAGE, sponsor.getDayWage()),
                new InformationDto(TEAM_MATCH_WAGE, sponsor.getMatchWage()),
                new InformationDto(TEAM_GOAL_WAGE, sponsor.getGoalBonusWage()),
                new InformationDto(TEAM_WIN_WAGE, sponsor.getWinWage()),
                new InformationDto(TEAM_DRAW_WAGE, sponsor.getDeuceWage()),
                new InformationDto(PROFICIT_DEFICIT, userTeam.getWealth()
                        .subtract(userTeam.getStartWealth()))
        ));
    }

    public static List<InformationDto> getExpenses(Team userTeam) {
        return new ArrayList<>(List.of(
                new InformationDto(LOANS_VALUE, userTeam.getLoans().size()),
                new InformationDto(TEAM_DAILY_EXPENSES, userTeam.getDailyExpenses()),
                new InformationDto(TEAM_WEEKLY_EXPENSES, userTeam.getWeeklyExpenses()),
                new InformationDto(TEAM_MONTHLY_EXPENSES, userTeam.getMonthlyExpenses()),
                new InformationDto(TEAM_TRANSFER_EXPENSES, userTeam.getTransferExpenses()),
                new InformationDto(TEAM_PERSONAL_EXPENSES, userTeam.getPersonalExpenses()),
                new InformationDto(TEAM_STADIUM_RENEWED_EXPENSES, userTeam.getStadiumExpenses()),
                new InformationDto(TEAM_MARKET_EXPENSES, userTeam.getMarketExpenses()),
                new InformationDto(PROFICIT_DEFICIT, userTeam.getWealth()
                        .subtract(userTeam.getStartWealth())))
        );
    }



}
