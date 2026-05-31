package ru.sargassov.fmweb.services.entity;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.DrawConverter;
import ru.sargassov.fmweb.intermediate_entities.*;
import ru.sargassov.fmweb.entities.DrawEntity;
import ru.sargassov.fmweb.repositories.entity.DrawRepository;
import ru.sargassov.fmweb.spi.intermediate_spi.CupIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.DrawIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.LeagueIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.intermediate_spi.TeamIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.entity.DrawServiceSpi;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DrawService implements DrawServiceSpi {

    private static final String DELIVER = "-";

    private final DrawRepository drawRepository;
    private final DrawConverter drawConverter;
    private final TeamIntermediateServiceSpi teamIntermediateService;
    private final DrawIntermediateServiceSpi drawIntermediateService;
    private final LeagueIntermediateServiceSpi leagueIntermediateService;
    private final CupIntermediateServiceSpi cupIntermediateService;

    @AllArgsConstructor
    public class Basket{
        private int draw;
        private Team team;
    }

    public List<DrawEntity> findAll(){
        return drawRepository.findAll();
    }

    public List<String> drawListMaker(){
        return findAll().stream()
                .map(DrawEntity::getTour)
                .collect(Collectors.toList());
    }

    private List<List<String>> leagueToursProject(){
        var projectDraws = drawListMaker();
        List<List<String>> tours = new ArrayList<>(projectDraws.size());

        for(var projectDraw : projectDraws){
            var splitProjectDraw = List.of(projectDraw.split("/"));
            tours.add(new ArrayList<>());
            tours.get(tours.size() - 1).addAll(splitProjectDraw);
        }

        return tours;
    }

    private List<List<String>> cupToursProject(){
        var firstCupTourSchedule = drawRepository.findCupRandomFirstTour();
        List<List<String>> tours = new ArrayList<>(1);

        var splitProjectDraw = Arrays.asList(firstCupTourSchedule.split("/"));
        tours.add(new ArrayList<>());
        tours.get(tours.size() - 1).addAll(splitProjectDraw);

        var secondCupTourSchedule = new ArrayList<String>();
        for (var draw : splitProjectDraw) {
            var reversedDraw = new StringBuilder(draw).reverse().toString();
            reversedDraw = reversedDraw.replace("61", "16");
            reversedDraw = reversedDraw.replace("51", "15");
            reversedDraw = reversedDraw.replace("41", "14");
            reversedDraw = reversedDraw.replace("31", "13");
            reversedDraw = reversedDraw.replace("21", "12");
            reversedDraw = reversedDraw.replace("01", "10");
            secondCupTourSchedule.add(reversedDraw);
        }
        tours.add(secondCupTourSchedule);
        return tours;
    }

    public List<Basket> drawBasket(User user) {
        var baskets = new ArrayList<Basket>();
        int count = 1;
        var teams = new ArrayList<>(teamIntermediateService.findAllByUser(user));
        var random = new Random();

        while (teams.size() > 0){
            int randInt = random.nextInt(teams.size());
            var basket = new Basket(count++, teams.remove(randInt));
            baskets.add(basket);
        }

        return baskets;
    }

    public String findIf(List<Basket> baskets, int condition){
        for(Basket b : baskets){
            if (b.draw == condition)
                return b.team.getName();
        }
        throw new RuntimeException("DrawService.findIf was get wrong condition " + condition);
    }

    @Override
    public void loadLeagueSchedule(User user){
        var league = leagueIntermediateService.getLeague(user);
        var projectOfSchedule = leagueToursProject();
        var baskets = drawBasket(user);
        loadSchedule(projectOfSchedule, baskets, user, league, null);;
    }

    @Override
    public void loadCupSchedule(User user) {
        var cup = cupIntermediateService.getCup(user);
        var projectOfSchedule = cupToursProject();
        var baskets = drawBasket(user);
        loadSchedule(projectOfSchedule, baskets, user, null, cup);
    }

    private void loadSchedule(List<List<String>> projectOfSchedule, List<DrawService.Basket> baskets,
                              User user, League league, Cup cup) {
        var drawList = new ArrayList<Draw>();
        int tourCount = 1;
        for (var tour : projectOfSchedule) {
            for (int match = 0; match < tour.size(); match++) {
                var currentMatch = tour.get(match);
                String[] splitTeamNumbers = currentMatch.split(DELIVER);
                splitTeamNumbers[0] = findIf(baskets, Integer.parseInt(splitTeamNumbers[0]));
                splitTeamNumbers[1] = findIf(baskets, Integer.parseInt(splitTeamNumbers[1]));
                var matchDescription = splitTeamNumbers[0] + DELIVER + splitTeamNumbers[1];
                var draw = drawConverter.getIntermediateEntityFromString(matchDescription, user, tourCount, league, cup);
                drawList.add(draw);
            }
            tourCount++;
        }
        drawIntermediateService.save(drawList);
    }
}
