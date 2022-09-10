CREATE TABLE IF NOT EXISTS teams (
                              id BIGSERIAL PRIMARY KEY NOT NULL,
                              name VARCHAR(32) NOT NULL,
                              manager VARCHAR(32) NOT NULL,
                              id_leagues BIGINT NOT NULL,
                              id_stadiums BIGINT NOT NULL,
                              id_cities BIGINT NOT NULL ,
                              team_wealth NUMERIC(6,2)
);