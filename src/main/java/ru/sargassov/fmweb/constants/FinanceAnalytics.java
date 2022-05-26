package ru.sargassov.fmweb.constants;

import ru.sargassov.fmweb.dto.FinanceDto;
import ru.sargassov.fmweb.intermediate_entites.Sponsor;
import ru.sargassov.fmweb.intermediate_entites.Team;

import java.util.ArrayList;
import java.util.List;

public class FinanceAnalytics {
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

    public static List<FinanceDto> getIncomes(Team userTeam){
        Sponsor sponsor = userTeam.getSponsor();

        return new ArrayList<>(List.of(
                new FinanceDto(TOTAL_BUDGET, userTeam.getWealth()),
                new FinanceDto(ALL_PLAYERS_COST, userTeam.getAllPlayersCost()),
                new FinanceDto(TEAM_SPONSOR, sponsor.getName()),
                new FinanceDto(TEAM_DAY_WAGE, sponsor.getDayWage()),
                new FinanceDto(TEAM_MATCH_WAGE, sponsor.getMatchWage()),
                new FinanceDto(TEAM_GOAL_WAGE, sponsor.getGoalBonusWage()),
                new FinanceDto(TEAM_WIN_WAGE, sponsor.getWinWage()),
                new FinanceDto(TEAM_DRAW_WAGE, sponsor.getDeuceWage()),
                new FinanceDto(PROFICIT_DEFICIT, userTeam.getWealth()
                        .subtract(userTeam.getStartWealth()))
        ));
    }

    public static List<FinanceDto> getExpenses(Team userTeam) {
        return new ArrayList<>(List.of(
                new FinanceDto(LOANS_VALUE, userTeam.getLoans().size()),
                new FinanceDto(TEAM_DAILY_EXPENSES, userTeam.getDailyExpenses()),
                new FinanceDto(TEAM_WEEKLY_EXPENSES, userTeam.getWeeklyExpenses()),
                new FinanceDto(TEAM_MONTHLY_EXPENSES, userTeam.getMonthlyExpenses()),
                new FinanceDto(TEAM_TRANSFER_EXPENSES, userTeam.getTransferExpenses()),
                new FinanceDto(TEAM_PERSONAL_EXPENSES, userTeam.getPersonalExpenses()),
                new FinanceDto(TEAM_STADIUM_RENEWED_EXPENSES, userTeam.getStadiumExpenses()),
                new FinanceDto(TEAM_MARKET_EXPENSES, userTeam.getMarketExpenses()),
                new FinanceDto(PROFICIT_DEFICIT, userTeam.getWealth()
                        .subtract(userTeam.getStartWealth())))
        );
    }



}
