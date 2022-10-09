CREATE TABLE IF NOT EXISTS stadiums (
                                 id BIGSERIAL PRIMARY KEY NOT NULL,
                                 id_teams BIGINT NOT NULL,
                                 title VARCHAR(32) NOT NULL,
                                 id_leagues BIGINT NOT NULL,
                                 full_capacity INTEGER NOT NULL,
                                 id_cities BIGINT NOT NULL
);