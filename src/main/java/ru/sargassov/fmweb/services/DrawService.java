package ru.sargassov.fmweb.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.api_temporary_classes_group.DrawApi;
import ru.sargassov.fmweb.intermediate_entites.Team;
import ru.sargassov.fmweb.entities.DrawEntity;
import ru.sargassov.fmweb.repositories.DrawRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DrawService {
    private final DrawRepository drawRepository;
    private final DrawApi drawApi;
    private final TeamService teamService;

    @AllArgsConstructor
    public class Basket{
        private int draw;
        private Team team;
    }

    public List<List<String>> getDrawsFromApi() {
        return drawApi.getSheduleApiList();
    }

    private List<DrawEntity> findAll(){
        return drawRepository.findAll();
    }

    private List<String> drawListMaker(){
        return findAll().stream()
                .map(DrawEntity::getTour)
                .collect(Collectors.toList());
    }

    private List<List<String>> toursProject(){
        List<String> projectDraws = drawListMaker();
        List<List<String>> tours = new ArrayList<>(projectDraws.size());

        for(String projectDraw : projectDraws){
            String[] splitProjectDraw = projectDraw.split("/");
            tours.add(new ArrayList<>());
            tours.get(tours.size() - 1).addAll(new ArrayList<>(List.of(splitProjectDraw)));
        }

        return tours;
    }

    private List<Basket> drawBasket() {
        List<Basket> baskets = new ArrayList<>();
        int count = 1;
        List<Team> teams = new ArrayList<>();
        teams.addAll(teamService.getTeamListFromApi());
        Random random = new Random();

        while (teams.size() > 0){
            int randInt = random.nextInt(teams.size());
            baskets.add(new Basket(count++, teams.remove(randInt)));
        }

        return baskets;
    }

    private String findIf(List<Basket> baskets, int condition){
        for(Basket b : baskets){
            if (b.draw == condition)
                return b.team.getName();
        }
        throw new RuntimeException("DrawService.findIf was get wrong condition " + condition);
    }

    public void loadShedule(){
        List<List<String>> projectOfShedule = toursProject();
        List<Basket> baskets = drawBasket();
        String deliver = "-";

        for (List<String> tour : projectOfShedule) {
            for (int match = 0; match < tour.size(); match++) {
                String[] splitTeamNumbers = tour.get(match).split(deliver);
                splitTeamNumbers[0] = findIf(baskets, Integer.parseInt(splitTeamNumbers[0]));
                splitTeamNumbers[1] = findIf(baskets, Integer.parseInt(splitTeamNumbers[1]));
                tour.set(match, splitTeamNumbers[0] + deliver + splitTeamNumbers[1]);
            }
        }

        drawApi.setSheduleApiList(projectOfShedule);
    }
}
