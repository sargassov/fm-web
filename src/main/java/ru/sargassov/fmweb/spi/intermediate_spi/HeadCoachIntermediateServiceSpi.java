package ru.sargassov.fmweb.spi.intermediate_spi;

import ru.sargassov.fmweb.intermediate_entities.HeadCoach;
import ru.sargassov.fmweb.intermediate_entities.User;

public interface HeadCoachIntermediateServiceSpi {
    HeadCoach save(HeadCoach headCoach);

    HeadCoach assignAndSave(User user, HeadCoach headCoach);
}
