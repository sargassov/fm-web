package ru.sargassov.fmweb.converters;

import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.matrix_dto.CortageDto;
import ru.sargassov.fmweb.intermediate_entities.Cortage;
import ru.sargassov.fmweb.intermediate_entities.Match;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CortageConverter {

    public CortageDto getCortageDtoFromCortage(Cortage cortage) {
        CortageDto cortageDto = new CortageDto();
        cortageDto.setCurrentName(cortage.getTeam().getName());
        var matchList = sortMatchlist(cortage.getMatchList());
        cortageDto.setWithAhmat(calculateScore(matchList.get(0)));
        cortageDto.setWithArsenal(calculateScore(matchList.get(1)));
        cortageDto.setWithCska(calculateScore(matchList.get(2)));
        cortageDto.setWithDinamo(calculateScore(matchList.get(3)));
        cortageDto.setWithKrasnodar(calculateScore(matchList.get(4)));
        cortageDto.setWithKrylia(calculateScore(matchList.get(5)));
        cortageDto.setWithLoko(calculateScore(matchList.get(6)));
        cortageDto.setWithOrenburg(calculateScore(matchList.get(7)));
        cortageDto.setWithRostov(calculateScore(matchList.get(8)));
        cortageDto.setWithRubin(calculateScore(matchList.get(9)));
        cortageDto.setWithSochi(calculateScore(matchList.get(10)));
        cortageDto.setWithSpartak(calculateScore(matchList.get(11)));
        cortageDto.setWithTambov(calculateScore(matchList.get(12)));
        cortageDto.setWithUral(calculateScore(matchList.get(13)));
        cortageDto.setWithUfa(calculateScore(matchList.get(14)));
        cortageDto.setWithZenit(calculateScore(matchList.get(15)));
        return cortageDto;
    }

    private List<Match> sortMatchlist(List<Match> matchList) {
        return matchList.stream()
                .sorted(Comparator.comparing(m -> m.getAway().getName()))
                .collect(Collectors.toList());
    }

    private String calculateScore(Match match) {
        if (match.isImpossibleMatch()) {
            return "X";
        }
        if (!match.isMatchPassed()) {
            return "-:-";
        }

        return match.getHomeScore() + ":" + match.getAwayScore();
    }
}
