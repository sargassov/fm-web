CREATE TABLE IF NOT EXISTS day (
                                     id bigserial PRIMARY KEY NOT NULL,
                                     id_user BIGINT NOT NULL,
                                     "date" date NOT NULL,
                                     changes VARCHAR(255)[],
                                     is_passed BOOLEAN NOT NULL,
                                     is_present BOOLEAN NOT NULL,
                                     is_league_day BOOLEAN NOT NULL,
                                     is_cup_day BOOLEAN NOT NULL,
                                     play_off_stage VARCHAR(64),
                                     count_of_tour INTEGER,
                                     notes_of_changes VARCHAR(255)[]
);