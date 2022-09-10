CREATE TABLE IF NOT EXISTS placement (
                                      id bigserial PRIMARY KEY NOT NULL,
                                      id_user BIGINT NOT NULL,
                                      id_team BIGINT NOT NULL,
                                      name VARCHAR(64) NOT NULL
);