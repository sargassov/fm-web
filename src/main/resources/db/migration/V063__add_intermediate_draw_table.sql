CREATE TABLE IF NOT EXISTS draw (
                                        id bigserial PRIMARY KEY NOT NULL,
                                        id_user BIGINT NOT NULL,
                                        id_league BIGINT,
                                        id_cup BIGINT,
                                        tour_number INTEGER NOT NULL,
                                        shedule VARCHAR(255) NOT NULL
);