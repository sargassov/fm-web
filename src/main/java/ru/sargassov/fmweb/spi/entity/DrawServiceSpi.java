package ru.sargassov.fmweb.spi.entity;

import ru.sargassov.fmweb.entities.DrawEntity;
import ru.sargassov.fmweb.intermediate_entities.User;
import ru.sargassov.fmweb.services.entity.DrawService;

import java.util.List;

public interface DrawServiceSpi {

    List<DrawEntity> findAll();

    List<String> drawListMaker();

    List<List<String>> toursProject();

    List<DrawService.Basket> drawBasket(User user);

    String findIf(List<DrawService.Basket> baskets, int condition);

    void loadShedule(User user);
}
