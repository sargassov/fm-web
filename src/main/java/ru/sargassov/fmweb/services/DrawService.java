package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.DrawConverter;
import ru.sargassov.fmweb.intermediate_entities.Team;
import ru.sargassov.fmweb.entities.DrawEntity;
import ru.sargassov.fmweb.entity_repositories.DrawRepository;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.intermediate_spi.DrawIntermediateServiceSpi;
import ru.sargassov.fmweb.intermediate_spi.TeamIntermediateServiceSpi;
import ru.sargassov.fmweb.spi.DrawServiceSpi;
import ru.sargassov.fmweb.spi.TeamServiceSpi;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DrawService implements DrawServiceSpi {
    private final DrawRepository drawRepository;
    private final DrawConverter drawConverter;
    private final TeamIntermediateServiceSpi teamIntermediateService;
    private final DrawIntermediateServiceSpi drawIntermediateService;

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

    public List<List<String>> toursProject(){
        var projectDraws = drawListMaker();
        List<List<String>> tours = new ArrayList<>(projectDraws.size());

        for(var projectDraw : projectDraws){
            var splitProjectDraw = List.of(projectDraw.split("/"));
            tours.add(new ArrayList<>());
            tours.get(tours.size() - 1).addAll(splitProjectDraw);
        }

        return tours;
    }

    public List<Basket> drawBasket() {
        var baskets = new ArrayList<Basket>();
        int count = 1;
        var teams = new ArrayList<>(teamIntermediateService.findAll());
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

    public void loadShedule(User user){
        var projectOfShedule = toursProject();
        var baskets = drawBasket();
        String deliver = "-";

        int tourCount = 1;
        for (var tour : projectOfShedule) {
            for (int match = 0; match < tour.size(); match++) {
                var currentMatch = tour.get(match);
                String[] splitTeamNumbers = currentMatch.split(deliver);
                splitTeamNumbers[0] = findIf(baskets, Integer.parseInt(splitTeamNumbers[0]));
                splitTeamNumbers[1] = findIf(baskets, Integer.parseInt(splitTeamNumbers[1]));
                tour.set(match, splitTeamNumbers[0] + deliver + splitTeamNumbers[1]);
                var draw = drawConverter.getIntermediateEntityFromString(currentMatch, user, tourCount);
                drawIntermediateService.save(draw);
            }
            tourCount++;
        }
    }
}
