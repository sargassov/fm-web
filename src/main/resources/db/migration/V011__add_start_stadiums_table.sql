CREATE TABLE IF NOT EXISTS stadiums (
                                 id BIGSERIAL PRIMARY KEY NOT NULL,
                                 id_teams BIGINTEGER NOT NULL,
                                 title VARCHAR(32) NOT NULL,
                                 id_leagues BIGINTEGER NOT NULL,
                                 full_capacity INTEGER NOT NULL,
                                 id_cities BIGINTEGER NOT NULL
);