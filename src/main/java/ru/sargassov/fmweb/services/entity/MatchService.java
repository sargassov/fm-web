package ru.sargassov.fmweb.services.entity;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sargassov.fmweb.constants.ConstantUtils;
import ru.sargassov.fmweb.constants.UserHolder;
import ru.sargassov.fmweb.converters.MatchConverter;
import ru.sargassov.fmweb.dto.days_dtos.DayDto;
import ru.sargassov.fmweb.dto.text_responses.PostMatchDto;
import ru.sargassov.fmweb.dto.text_responses.PreMatchDto;
import ru.sargassov.fmweb.intermediate_entities.Goal;
import ru.sargassov.fmweb.intermediate_entities.Match;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.repositories.intermediate_repositories.MatchIntermediateRepository;
import ru.sargassov.fmweb.spi.intermediate_spi.DayIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.DrawIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.GoalIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.MatchIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.PlacementIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.TeamIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.entity.MatchServiceSpi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class MatchService implements MatchServiceSpi {
    private DrawIntermediateServiceSpi drawIntermediateService;
    private DayIntermediateServiceSpi dayIntermediateService;
    private PlacementIntermediateServiceSpi placementIntermediateService;
    private TeamIntermediateServiceSpi teamIntermediateService;
    private MatchIntermediateServiceSpi matchIntermediateService;
    private MatchConverter matchConverter;
    private GoalIntermediateServiceSpi goalIntermediateService;
    private MatchIntermediateRepository repository;
    @Override
    public void loadmatches(User user) {
        var drawList = drawIntermediateService.findAllByUser(user);
        var notSavedMatches = new ArrayList<Match>();
        for (var draw : drawList) {
            var match = matchConverter.getIntermediateEntityFromDraw(draw, user);
            notSavedMatches.add(match);
        }
        matchIntermediateService.save(notSavedMatches);
    }

    @Override
    public List<PreMatchDto> getPreMatchInfo() {
        var preMatchDtos = new ArrayList<PreMatchDto>();

        var currentDay = dayIntermediateService.findByPresent();
        var userTeamId = UserHolder.user.getUserTeam().getId();
        var userTeam = teamIntermediateService.getById(userTeamId);
        var userTeamMatch = currentDay.getUserTeamMatch(userTeam);
        var userTeamMatchHomeId = userTeamMatch.getHome().getId();
        var userTeamMatchAwayId = userTeamMatch.getAway().getId();
        var userTeamMatchHome = teamIntermediateService.getById(userTeamMatchHomeId);
        var userTeamMatchAway = teamIntermediateService.getById(userTeamMatchAwayId);
        var userTeamMatchHomeDesc = userTeamMatch.getHome().getName();
        var userTeamMatchAwayDesc = userTeamMatch.getAway().getName();
        var homeMatchApplication = userTeamMatchHome.getMatchApplication().stream()
                .map(ConstantUtils::preMatchPlayerFormUtil)
                .collect(Collectors.toList());
        var awayMatchApplication = userTeamMatchAway.getMatchApplication().stream()
                .map(ConstantUtils::preMatchPlayerFormUtil)
                .collect(Collectors.toList());
        var opponentTeam = userTeamMatchHome.equals(userTeam)
                ? userTeamMatchAway
                : userTeamMatchHome;
        placementIntermediateService.optimalOpponentPlacement(opponentTeam);

        var preMatchDto = new PreMatchDto();
        preMatchDto.setPreMatchHomeInfo(userTeamMatchHomeDesc.toUpperCase() + " ((" + userTeamMatchHome.getTeamPower() + ")) ");
        preMatchDto.setPreMatchAwayInfo(userTeamMatchAwayDesc.toUpperCase() + " ((" + userTeamMatchAway.getTeamPower() + ")) ");
        preMatchDtos.add(preMatchDto);
        for (var x = homeMatchApplication.size() - 1; x >= 0; x--) {
            var dto = new PreMatchDto();
            var homePlayer = homeMatchApplication.get(x);
            var awayPlayer = awayMatchApplication.get(x);
            dto.setPreMatchHomeInfo(homePlayer);
            dto.setPreMatchAwayInfo(awayPlayer);
            preMatchDtos.add(dto);
        }
        return preMatchDtos;
    }

    @Override
    @Transactional
    public void imitate(DayDto dayDto) {
        var day = dayDto.getDay();
        var month = ConstantUtils.monthsGetint(dayDto.getMonth());
        var year = dayDto.getYear();
        var date = LocalDate.of(year, month, day);
        var currentDay = dayIntermediateService.findByDate(date);
        var matches = currentDay.getMatches();
        for (var m : matches) {
            MatchImitateProcessor.imitateMatch(m);
        }
        goalIntermediateService.saveAllByMatches(matches);
    }

    @Override
    public List<PostMatchDto> getPostMatchInfo() {
        var currentDay = dayIntermediateService.findByPresent();
        if (!currentDay.isMatch()) {
            throw new IllegalStateException("Present day without matches");
        }

        var dtos = new ArrayList<PostMatchDto>();
        var matches = currentDay.getMatches();
        for (var match : matches) {
            var postMatchDto = new PostMatchDto();
            var homeTeamDesc = match.getHome().getName();
            var awayTeamDesc = match.getAway().getName();
            var homeScore = 0;
            var awayScore = 0;
            postMatchDto.setMatch(postMatchTitleConstruction(homeTeamDesc, awayTeamDesc, match));
            var goalList = constructGoalList(match);
            var goalListString = new ArrayList<String>();
            for (var goal : goalList) {
                var player = goal.getScorePlayer();
                var team = player.getTeam().getName();
                if (goal.getTeam().getName().equals(homeTeamDesc)) {
                    homeScore++;
                } else {
                    awayScore++;
                }
                goalListString.add("[" + homeScore + "-" + awayScore + "] " + team + " " + player.getName() + " " + goal.getMin());
            }
            postMatchDto.setGoals(goalListString);
            dtos.add(postMatchDto);
        }
        return dtos;
    }

    private List<Goal> constructGoalList(Match match) {
        return match.getGoals().stream()
                .sorted(Comparator.comparing(Goal::getMin))
                .collect(Collectors.toList());
    }

    private String postMatchTitleConstruction(String homeTeamDesc, String awayTeamDesc, Match match) {
        return homeTeamDesc + " - " + awayTeamDesc + " (" + match.getHomeScore() + ":" + match.getAwayScore() + ")";
    }














}
