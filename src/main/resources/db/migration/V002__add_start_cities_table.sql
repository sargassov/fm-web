CREATE TABLE IF NOT EXISTS cities (
                               id BIGSERIAL PRIMARY KEY NOT NULL,
                               name VARCHAR(32),
                               id_leagues BIGINT NOT NULL,
                               id_teams BIGINT
);