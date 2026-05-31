CREATE TABLE IF NOT EXISTS cup (
                                      id bigserial PRIMARY KEY NOT NULL,
                                      id_user BIGINT NOT NULL,
                                      id_league BIGINT NOT NULL,
                                      team_value int4 NOT NULL,
                                      name VARCHAR(64) NOT NULL
);