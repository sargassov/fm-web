package ru.sargassov.fmweb.spi;

import ru.sargassov.fmweb.entities.DrawEntity;
import ru.sargassov.fmweb.services.DrawService;

import java.util.List;

public interface DrawServiceSpi {

    List<List<String>> getDrawsFromApi();

    List<DrawEntity> findAll();

    List<String> drawListMaker();

    List<List<String>> toursProject();

    List<DrawService.Basket> drawBasket();

    String findIf(List<DrawService.Basket> baskets, int condition);

    void loadShedule();
}
