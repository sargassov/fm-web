package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.dto.LeagueDto;
import ru.sargassov.fmweb.intermediate_entites.League;

public interface LeagueServiceSpi {

   League getRussianLeague();

   LeagueDto getLeagueName();
}
