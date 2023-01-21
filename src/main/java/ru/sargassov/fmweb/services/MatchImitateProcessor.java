package ru.sargassov.fmweb.services;

import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.intermediate_entities.Goal;
import ru.sargassov.fmweb.intermediate_entities.Match;
import ru.sargassov.fmweb.intermediate_entities.Player;
import ru.sargassov.fmweb.intermediate_entities.Team;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static ru.sargassov.fmweb.constants.TextConstant._3_4_3;
import static ru.sargassov.fmweb.constants.TextConstant._3_5_2;
import static ru.sargassov.fmweb.constants.TextConstant._4_3_3;
import static ru.sargassov.fmweb.constants.TextConstant._4_4_2;
import static ru.sargassov.fmweb.constants.TextConstant._4_5_1;
import static ru.sargassov.fmweb.constants.TextConstant._5_4_1;

public final class MatchImitateProcessor {

    private MatchImitateProcessor() {
        throw new IllegalStateException("Utility class");
    }
    public static void imitateMatch(Match m) {
        var home = m.getHome();
        var away = m.getAway();
        var homeCoeff = BigDecimal.ZERO;
        var awayCoeff = BigDecimal.ZERO;
        homeCoeff = homeCoeff.add(homeStadiumPower(home));
        teamPowerCompare(home, away, homeCoeff, awayCoeff);
        captainPowerCompare(home, away, homeCoeff, awayCoeff);
        injuryPowerImitate(home, away, homeCoeff, awayCoeff);
        placementPowerCompare(home, away, homeCoeff, awayCoeff);
        scoreImitate(homeCoeff, awayCoeff, home, away, m);
    }

    private static void placementPowerCompare(Team home, Team away, BigDecimal homeCoeff, BigDecimal awayCoeff) {
        var homePlacement = home.getPlacement().getName();
        var awayPlacement = away.getPlacement().getName();
        placementSwitcher(homePlacement, awayPlacement, homeCoeff);
        placementSwitcher(awayPlacement, homePlacement, awayCoeff);
    }

    private static void placementSwitcher(String placement, String otherPlacement, BigDecimal coeff) {
        switch (placement) {
            case _4_4_2:
                placementResolution(coeff, _3_5_2, _3_4_3, otherPlacement);
            case _3_5_2:
                placementResolution(coeff, _3_4_3, _4_5_1, otherPlacement);
            case _3_4_3:
                placementResolution(coeff, _4_5_1, _5_4_1, otherPlacement);
            case _4_5_1:
                placementResolution(coeff, _5_4_1, _4_3_3, otherPlacement);
            case _5_4_1:
                placementResolution(coeff, _4_3_3, _4_4_2, otherPlacement);
            case _4_3_3:
                placementResolution(coeff, _4_4_2, _3_5_2, otherPlacement);
        }
    }

    private static void placementResolution(BigDecimal coeff, String firstPlacement, String secondPlacement, String opponentPlacement) {
        if (opponentPlacement.equals(firstPlacement) || opponentPlacement.equals(secondPlacement)) {
            coeff = coeff.add(BigDecimal.valueOf(1.5));
        }
    }

    private static void teamPowerCompare(Team home, Team away, BigDecimal homeCoeff, BigDecimal awayCoeff) {
        var homeTeamPower = home.getTeamPower();
        var awayTeamPower = away.getTeamPower();
        var teamPowerCompareInt = homeTeamPower - awayTeamPower;
        if (teamPowerCompareInt > 20) {
            homeCoeff = homeCoeff.add(BigDecimal.valueOf(3.0));
        } else if (teamPowerCompareInt > 15) {
            homeCoeff = homeCoeff.add(BigDecimal.valueOf(2.0));
        } else if (teamPowerCompareInt > 10) {
            homeCoeff = homeCoeff.add(BigDecimal.valueOf(1.0));
        } else if (teamPowerCompareInt > 5) {
            homeCoeff = homeCoeff.add(BigDecimal.valueOf(0.5));
        } else if (teamPowerCompareInt > 1) {
            homeCoeff = homeCoeff.add(BigDecimal.valueOf(0.1));
        } else if (teamPowerCompareInt > -5 && teamPowerCompareInt < 0) {
            awayCoeff = awayCoeff.add(BigDecimal.valueOf(0.1));
        } else if (teamPowerCompareInt > -10) {
            awayCoeff = awayCoeff.add(BigDecimal.valueOf(0.5));
        } else if (teamPowerCompareInt > -15) {
            awayCoeff = awayCoeff.add(BigDecimal.valueOf(1.0));
        } else if (teamPowerCompareInt > -20) {
            awayCoeff = awayCoeff.add(BigDecimal.valueOf(1.5));
        } else {
            awayCoeff = awayCoeff.add(BigDecimal.valueOf(2.0));
        }
    }

    private static BigDecimal homeStadiumPower(Team home) {
        var stadium = home.getStadium();
        var visitors = stadium.getUsualAverageCapacity();
        double value;

        if (visitors < 5000) {
            value = 0.3;
        } else if (visitors < 10000) {
            value = 0.7;
        } else if (visitors < 12000) {
            value = 0.9;
        } else if (visitors < 14000) {
            value = 1.1;
        } else if (visitors < 18000) {
            value = 1.4;;
        } else if (visitors < 25000) {
            value = 1.9;
        } else if (visitors < 35000) {
            value = 2.4;
        } else if (visitors < 45000) {
            value = 2.9;
        } else {
            value = 3.0;
        }
        return BigDecimal.valueOf(value);
    }

    private static void scoreImitate(BigDecimal homeCoeff, BigDecimal awayCoeff, Team home, Team away, Match match) {
        //todo тестовая имитация матча
        var user = UserHolder.user;
        match.setMatchPassed(true);
        if (homeCoeff.compareTo(awayCoeff) == 0) {
            match.setHomeScore(0);
            match.setAwayScore(0);
            match.setGoals(new ArrayList<>());
            home.setGames(home.getGames() + 1);
            home.setDrawn(home.getDrawn() + 1);
            home.setScored(home.getScored() + 0);
            home.setMissed(home.getMissed() + 0);
            away.setGames(away.getGames() + 1);
            away.setDrawn(away.getDrawn() + 1);
            away.setScored(away.getScored() + 0);
            away.setMissed(away.getMissed() + 0);
        } else if (homeCoeff.compareTo(awayCoeff) > 0) {
            match.setHomeScore(3);
            match.setAwayScore(0);
            match.setGoals(List.of(
                    new Goal(home.findPlayerByStrategyPlace(10), home, 5, match, user),
                    new Goal(home.findPlayerByStrategyPlace(9), home, 45, match, user),
                    new Goal(home.findPlayerByStrategyPlace(8), home, 85, match, user)
            ));
            home.setGames(home.getGames() + 1);
            home.setWon(home.getWon() + 1);
            home.setScored(home.getScored() + 3);
            home.setMissed(home.getMissed() + 0);
            away.setGames(away.getGames() + 1);
            away.setLost(away.getLost() + 1);
            away.setScored(away.getScored() + 0);
            away.setMissed(away.getMissed() + 3);
        } else if (homeCoeff.compareTo(awayCoeff) < 0) {
            match.setHomeScore(0);
            match.setAwayScore(3);
            match.setGoals(List.of(
                    new Goal(away.findPlayerByStrategyPlace(10), away, 5, match, user),
                    new Goal(away.findPlayerByStrategyPlace(9), away, 45, match, user),
                    new Goal(away.findPlayerByStrategyPlace(8), away, 85, match, user)
            ));
            home.setGames(home.getGames() + 1);
            home.setLost(home.getLost() + 1);
            home.setScored(home.getScored() + 0);
            home.setMissed(home.getMissed() + 3);
            away.setGames(away.getGames() + 1);
            away.setWon(away.getWon() + 1);
            away.setScored(away.getScored() + 3);
            away.setMissed(away.getScored() + 0);
        }
    }

    private static void timeBeforeTreatImitate(Player randomInjuryPlayer) {
        var randomTreatmentCoeff = Math.random();
        int timeBeforeTreatment;
        if (randomTreatmentCoeff == 0.99) {
            timeBeforeTreatment = 999;
        } else if (randomTreatmentCoeff > 0.97) {
            timeBeforeTreatment = 100;
        } else if (randomTreatmentCoeff > 0.95) {
            timeBeforeTreatment = 80;
        } else if (randomTreatmentCoeff > 0.92) {
            timeBeforeTreatment = 50;
        } else if (randomTreatmentCoeff > 0.88) {
            timeBeforeTreatment = 30;
        } else if (randomTreatmentCoeff > 0.8) {
            timeBeforeTreatment = 25;
        } else if (randomTreatmentCoeff > 0.5) {
            timeBeforeTreatment = 10;
        } else if (randomTreatmentCoeff > 0.3) {
            timeBeforeTreatment = 8;
        } else {
            timeBeforeTreatment = 5;
        }
        randomInjuryPlayer.setTimeBeforeTreat(timeBeforeTreatment);
    }

    private static void newInjuriesInMatchTime(Team team) {
        var randomVar = Math.random();
        if (randomVar < 0.95) {
            return;
        }
        var teamPlayers = team.getMatchApplication();
        var randomInjuryPlayer = teamPlayers.get((int) (Math.random() * teamPlayers.size()));
        var randomInjuryPlayerPosition = randomInjuryPlayer.getPosition();
        randomInjuryPlayer.setInjury(true);
        timeBeforeTreatImitate(randomInjuryPlayer);
        Player bestSubstitutionPlayerOnPosition;
        bestSubstitutionPlayerOnPosition = team.getBestSubstitution(randomInjuryPlayerPosition);
        if (isNull(bestSubstitutionPlayerOnPosition)) {
            bestSubstitutionPlayerOnPosition = team.getAnyValidSubstitution(randomInjuryPlayerPosition);
        }
        var randomInjuryPlayerStrategyPlace = randomInjuryPlayer.getStrategyPlace();
        randomInjuryPlayer.setStrategyPlace(-100);
        randomInjuryPlayer.setTrainingBalance(randomInjuryPlayer.getTrainingAble() / 2);
        if (bestSubstitutionPlayerOnPosition != null) {
            bestSubstitutionPlayerOnPosition.setStrategyPlace(randomInjuryPlayerStrategyPlace);
        }
        team.powerTeamCounter();
    }

    private static void injuryPowerImitate(Team home, Team away, BigDecimal homeCoeff, BigDecimal awayCoeff) {
        var homeInjuries = home.getInjuriesValue();
        var awayInjuries = away.getInjuriesValue();
        injuriesDisorder(homeInjuries, homeCoeff);
        injuriesDisorder(awayInjuries, awayCoeff);
        newInjuriesInMatchTime(home); //todo нужна ли сущность Injury?
        newInjuriesInMatchTime(away);
    }

    private static void injuriesDisorder(Long injuries, BigDecimal coeff) {
        if (injuries > 5) {
            coeff = coeff.subtract(BigDecimal.valueOf(2.5));
        } else if (injuries > 3) {
            coeff = coeff.subtract(BigDecimal.valueOf(2.0));
        } else if (injuries == 3) {
            coeff = coeff.subtract(BigDecimal.valueOf(1.6));
        } else if (injuries == 2) {
            coeff = coeff.subtract(BigDecimal.valueOf(1.0));
        } else if (injuries == 1) {
            coeff = coeff.subtract(BigDecimal.valueOf(0.3));
        }
    }

    private static void captainPowerCompare(Team home, Team away, BigDecimal homeCoeff, BigDecimal awayCoeff) {
        var homePlayers = home.getMatchApplication();
        var awayPlayers = away.getMatchApplication();
        var homeCaptain = home.getCaptainOfTeam();
        var awayCaptain = away.getCaptainOfTeam();
        if (homePlayers.contains(homeCaptain)) {
            homeCoeff.add(BigDecimal.valueOf(captainAblePower(homeCaptain)));
        }
        if (awayPlayers.contains(awayCaptain)) {
            awayCoeff.add(BigDecimal.valueOf(captainAblePower(awayCaptain)));
        }
    }

    private static double captainAblePower(Player captain) {
        var captainAble = captain.getCaptainAble();
        double coeff;
        if (captainAble > 90) {
            coeff = 1.5;
        } else if (captainAble > 80) {
            coeff = 1.0;
        } else if (captainAble > 70) {
            coeff = 0.8;
        } else if (captainAble > 60) {
            coeff = 0.6;
        } else if (captainAble > 50) {
            coeff = 0.4;
        } else if (captainAble > 40) {
            coeff = 0.2;
        } else {
            coeff = 0.1;
        }
        return coeff;
    }
}
