CREATE TABLE IF NOT EXISTS league (
                                          id bigserial PRIMARY KEY NOT NULL,
                                          id_user BIGINT NOT NULL,
                                          team_value int4 NOT NULL,
                                          name VARCHAR(64) NOT NULL
);