CREATE TABLE IF NOT EXISTS days (
                             id BIGSERIAL PRIMARY KEY NOT NULL,
                             day DATE NOT NULL,
                             is_passed BOOLEAN NOT NULL,
                             is_present BOOLEAN NOT NULL,
                             is_league_day BOOLEAN NOT NULL,
                             is_cup_day BOOLEAN NOT NULL
);